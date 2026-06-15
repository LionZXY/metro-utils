package uk.kulikov.metro.assisted

import kotlin.reflect.KClass

/**
 * Automatically generates an assisted factory for the annotated class and contributes
 * it to the specified [scope] as binding of the type specified as [boundType].
 *
 * [boundType] should be treated the same way as regular Metro @AssistedFactory.
 * [boundType] should conform to the same requirements as regular Metro @AssistedFactory.
 * Under Metro 1.x the [boundType] factory-method parameters are matched against the
 * implementation constructor's @Assisted parameters by type, name and order — there are no
 * string assisted keys. If a factory-method parameter name differs from the constructor
 * parameter name, annotate it with @AssistedKey("<constructorParamName>").
 *
 * Usage example:
 *
 * ```
 * abstract class AppScope private constructor()
 *
 * interface MyClass
 *
 * interface MyFactory {
 *   fun create(
 *     assistedParam: Int,
 *   ): MyClass
 * }
 *
 * @AssistedInject
 * @ContributesAssistedFactory(AppScope::class, MyFactory::class)
 * class DefaultMyClass(
 *   regularParam: String,
 *   @Assisted assistedParam: Int
 * ) : MyClass
 * ```
 *
 * The following factory will be generated, implementing MyFactory:
 *
 * ```
 * @ContributesBinding(AppScope::class, binding<MyFactory>())
 * @AssistedFactory
 * abstract class MyClass_AssistedFactory : MyFactory {
 *   override fun create(
 *      assistedParam: Int,
 *   ): DefaultMyClass
 * }
 * ```
 *
 * @param scope The scope to contribute the generated factory to.
 * @param boundType The type that the generated factory will implement or extend.
 */
@Target(AnnotationTarget.CLASS)
public annotation class ContributesAssistedFactory(
    val scope: KClass<*>,
    val boundType: KClass<*>,
)