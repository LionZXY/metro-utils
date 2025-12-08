package me.gulya.metro.utils.sample

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import me.gulya.metro.assisted.ContributesAssistedFactory

@ContributesAssistedFactory(SampleScope::class, TestApi.Factory::class)
class DefaultTestApi @AssistedInject constructor(
    @Assisted("arg2") private val arg1: String,
    @Assisted("arg1") private val arg: Int,
) : TestApi