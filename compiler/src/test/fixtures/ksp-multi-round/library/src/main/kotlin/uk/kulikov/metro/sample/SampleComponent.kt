package uk.kulikov.metro.sample

@GenerateClass // This will trigger our KSP processor to generate SampleComponentGenerated
interface SampleComponent {
    fun interface Factory {
        operator fun invoke(str: String): SampleComponent
    }
}