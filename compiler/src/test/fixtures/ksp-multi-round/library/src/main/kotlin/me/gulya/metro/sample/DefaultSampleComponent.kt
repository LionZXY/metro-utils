package me.gulya.metro.sample

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.Inject
import me.gulya.metro.assisted.ContributesAssistedFactory

@ContributesAssistedFactory(SampleScope::class, SampleComponent.Factory::class)
class DefaultSampleComponent @Inject constructor(
    val generated: SampleComponentGenerated, // This depends on the generated class
    @Assisted val string: String,
) : SampleComponent