plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(projects.processorApi)
    implementation(projects.di)
    implementation(projects.library)
    implementation(libs.metro.utils.annotations)

    ksp(projects.processor)
    ksp(libs.metro.utils.compiler)
} 