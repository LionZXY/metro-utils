plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        optIn.add("dev.zacsweers.metro.ExperimentalMetroApi")
    }
}