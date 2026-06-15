internal object Errors {
    fun missingBoundType(className: String): String {
        return "The @ContributesAssistedFactory annotation on class '$className' " +
                "must have a 'boundType' parameter"
    }

    fun mustHaveSinglePrimaryConstructor(className: String): String {
        return "Class '$className' annotated with @ContributesAssistedFactory " +
                "must have a single primary constructor"
    }

    fun primaryConstructorMustBeAnnotatedWithAssistedInject(className: String): String {
        return "Class '$className' annotated with @ContributesAssistedFactory " +
                "must have its primary constructor annotated with @AssistedInject"
    }

    fun boundTypeMustBeAbstractOrInterface(boundTypeName: String, assistedFactoryName: String): String {
        return "The bound type '$boundTypeName' for @ContributesAssistedFactory on class " +
                "'$assistedFactoryName' must be an abstract class or interface"
    }

    fun boundTypeMustHasSingleAbstractMethod(boundType: String): String {
        return "The bound type '$boundType' for @ContributesAssistedFactory " +
                "must have a single abstract method"
    }

    fun parameterMismatch(boundTypeName: String, factoryMethodName: String, assistedFactoryName: String): String {
        return "The assisted factory method parameters in '$boundTypeName.$factoryMethodName' " +
                "must match the @Assisted parameters in the primary constructor of " +
                "'$assistedFactoryName'"
    }

    fun parameterDoesNotMatchAssistedParameter(factoryParameterName: String, assistedFactoryName: String): String {
        return "The factory method parameter '${factoryParameterName}' does not match any @Assisted parameter " +
                "in the primary constructor of '${assistedFactoryName}' by type and name. Under Metro 1.x " +
                "assisted parameters are matched by type and name; use @AssistedKey(\"<constructorParamName>\") " +
                "on the factory method parameter if its name differs from the constructor parameter name"
    }

    fun boundTypeMustBeClassOrInterface(boundTypeName: String): String {
        return "Bound type ${boundTypeName} must be a class or interface"
    }

}
