package me.gulya.metro.utils

import com.squareup.kotlinpoet.TypeName

internal data class ParameterKey(
    val type: TypeName,
    val assistedKey: String?,
)