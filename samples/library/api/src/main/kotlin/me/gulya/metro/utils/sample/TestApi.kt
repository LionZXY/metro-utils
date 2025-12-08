package me.gulya.metro.utils.sample

import me.gulya.metro.assisted.AssistedKey

interface TestApi {
    interface Factory {
        fun create(
            @AssistedKey("arg1") arg: Int,
            @AssistedKey("arg2") arg1: String
        ): TestApi
    }
}

abstract class SampleScope