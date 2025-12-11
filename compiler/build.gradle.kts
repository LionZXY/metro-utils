plugins {
    id("conventions.library")
    id("conventions.publish")
    alias(libs.plugins.ksp)
}

publish {
    configurePom(
        artifactId = "compiler",
        pomName = "Metro Utils Compiler",
        pomDescription = "Code generator for metro-utils",
    )
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}

dependencies {
    implementation(projects.annotations)

    implementation(libs.metro.runtime)

    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)

    implementation(libs.ksp.api)

    implementation(libs.google.autoservice.annotations)
    ksp(libs.google.autoservice.ksp)

    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    testImplementation(libs.kctfork.core)
    testImplementation(libs.kctfork.ksp)

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test"))
}