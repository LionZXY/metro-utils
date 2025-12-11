package uk.kulikov.metro.sample

import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(SampleScope::class)
interface AppComponent {
    val factory: SampleComponent.Factory
}

fun main() {
    val component = createGraph<AppComponent>()
    val instance = component.factory("")
}