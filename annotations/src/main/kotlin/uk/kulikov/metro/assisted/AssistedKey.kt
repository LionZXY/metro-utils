package uk.kulikov.metro.assisted

/**
 * Maps a bound-type factory-method parameter to a constructor `@Assisted` parameter by name.
 *
 * Under Metro 1.x assisted parameters no longer carry string identifiers — they are matched
 * between the bound type's factory method and the assisted-injection constructor by type, name
 * and order. By default metro-utils matches them using the factory-method parameter name.
 *
 * Use [AssistedKey] on a bound-type factory-method parameter only when its name differs from the
 * corresponding constructor `@Assisted` parameter name. The [value] is then treated as the
 * expected constructor parameter name to match against.
 *
 * Note: the generated `@AssistedFactory` never emits a string `@Assisted` value; the generated
 * factory-method parameters are simply named after the matched constructor parameters.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
public annotation class AssistedKey(
    val value: String,
)
