package uk.kulikov.metro.utils.sample

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import uk.kulikov.metro.assisted.ContributesAssistedFactory

@ContributesAssistedFactory(SampleScope::class, TestApi.Factory::class)
class DefaultTestApi @AssistedInject constructor(
    @Assisted private val arg: Int,
    @Assisted private val arg1: String,
) : TestApi
