@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "ksp-multi-round-fixture"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../../../../../gradle/libs.versions.toml"))
        }
    }
}

include(":processor")
include(":processor-api")
include(":library")
include(":di")
include(":app")


includeBuild("../../../../../") {
    dependencySubstitution {
        substitute(module("uk.kulikov.metro:annotations")).using(project(":annotations"))
        substitute(module("uk.kulikov.metro:compiler")).using(project(":compiler"))
    }
}