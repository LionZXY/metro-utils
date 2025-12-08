package me.gulya.metro.utils.sample

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.Inject
import me.gulya.metro.assisted.ContributesAssistedFactory

@ContributesAssistedFactory(SampleScope::class, TestApi.Factory::class)
class DefaultTestApi @Inject constructor(
    @Assisted("arg2") private val arg1: String,
    @Assisted("arg1") private val arg: Int,
) : TestApi