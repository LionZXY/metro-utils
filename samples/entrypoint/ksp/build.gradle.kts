plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs = listOf("-Xextended-compiler-checks")
    }

}

dependencies {
    ksp(projects.compiler)
    implementation(projects.annotations)
    implementation(projects.samples.library.api)
    implementation(projects.samples.library.impl.ksp)
}