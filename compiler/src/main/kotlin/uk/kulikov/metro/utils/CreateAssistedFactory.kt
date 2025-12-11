package uk.kulikov.metro.utils

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory

fun createAssistedFactory(
    annotatedName: ClassName,
    boundType: BoundType,
    scope: ClassName,
    factoryMethod: FactoryMethod,
): AssistedFactorySpec {
    val factoryClassName = "${annotatedName.simpleName}_AssistedFactory"

    // Always generate an abstract class (not interface) so Metro's @ContributesBinding works
    val typeBuilder = TypeSpec
        .classBuilder(factoryClassName)
        .addModifiers(KModifier.ABSTRACT)
        .apply {
            if (boundType.isInterface) {
                addSuperinterface(boundType.name)
            } else {
                superclass(boundType.name)
            }
        }

    val spec = FileSpec.builder(annotatedName.packageName, factoryClassName).apply {
        addImport("dev.zacsweers.metro", "binding")
        addType(
            typeBuilder
                .addAnnotation(
                    AnnotationSpec
                        .builder(ClassName("dev.zacsweers.metro", "ContributesBinding"))
                        .addMember("%T::class, binding<%T>()", scope, boundType.name)
                        .build(),
                )
                .addAnnotation(AssistedFactory::class)
                .addFunction(
                    FunSpec
                        .builder(factoryMethod.name)
                        .addModifiers(KModifier.OVERRIDE, KModifier.ABSTRACT)
                        .apply {
                            factoryMethod.parameters.forEach { parameter ->
                                addParameter(
                                    ParameterSpec
                                        .builder(parameter.name, parameter.type)
                                        .assisted(parameter.assistedKeyValue)
                                        .build(),
                                )
                            }
                        }
                        .returns(annotatedName)
                        .build(),
                )
                .build(),
        )
    }.build()
    return AssistedFactorySpec(
        name = factoryClassName,
        packageName = annotatedName.packageName,
        spec = spec,
    )
}

data class AssistedFactorySpec(
    val name: String,
    val packageName: String,
    val spec: FileSpec,
)

data class BoundType(
    val name: ClassName,
    val isInterface: Boolean,
)

data class FactoryMethod(
    val name: String,
    val parameters: List<FactoryMethodParameter>,
)

data class FactoryMethodParameter(
    val type: TypeName,
    val name: String,
    val assistedKeyValue: String?
)

private fun ParameterSpec.Builder.assisted(value: String?): ParameterSpec.Builder {
    if (value == null) return this
    addAnnotation(
        AnnotationSpec
            .builder(Assisted::class)
            .apply {
                addMember("%S", value)
            }
            .build(),
    )
    return this
}
