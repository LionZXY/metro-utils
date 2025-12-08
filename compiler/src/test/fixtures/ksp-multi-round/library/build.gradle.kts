plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(projects.processorApi)

    implementation(projects.di)
    implementation(libs.metro.utils.annotations)

    // Metro runtime provides @Assisted, @Inject annotations
    implementation(libs.metro.runtime)

    ksp(libs.metro.utils.compiler)
    ksp(projects.processor)
}