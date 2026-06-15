package uk.kulikov.metro.utils.sample

interface TestApi {
    interface Factory {
        fun create(
            arg: Int,
            arg1: String,
        ): TestApi
    }
}

abstract class SampleScope
