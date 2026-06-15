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
    // The KspMultiRoundFunctionalTest spawns a nested Gradle build (via GradleTestKit) that applies
    // the Metro 1.2.1 Gradle plugin, which requires a JVM runtime >= 21. GradleTestKit inherits the
    // JVM running the test, so the test task must execute on a 21+ JDK even though jvmTarget stays 17.
    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    )
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