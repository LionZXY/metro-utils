package uk.kulikov.metro.utils.sample

import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph

@DependencyGraph(SampleScope::class)
interface SampleComponent {
    val factory: TestApi.Factory
}

fun main(args: Array<String>) {
    val component: SampleComponent = createGraph()
    val testApi = component.factory.create(1, "arg2")
}