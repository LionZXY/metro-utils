package me.gulya.metro.sample

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import me.gulya.metro.assisted.ContributesAssistedFactory

@AssistedInject
@ContributesAssistedFactory(SampleScope::class, SampleComponent.Factory::class)
class DefaultSampleComponent(
    val generated: SampleComponentGenerated, // This depends on the generated class
    @Assisted val string: String,
) : SampleComponent