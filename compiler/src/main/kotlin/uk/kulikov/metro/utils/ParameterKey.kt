package uk.kulikov.metro.utils

import com.squareup.kotlinpoet.TypeName

/**
 * Identity of an assisted parameter under Metro 1.x.
 *
 * Metro 1.x no longer supports string-based assisted keys (`@Assisted("key")` was removed).
 * Same-type assisted parameters are disambiguated by their parameter [name] instead. Matching
 * between the bound type's factory-method parameters and the assisted-injection constructor's
 * `@Assisted` parameters is therefore done by type + name (and order is preserved by generation).
 */
internal data class ParameterKey(
    val type: TypeName,
    val name: String,
)
