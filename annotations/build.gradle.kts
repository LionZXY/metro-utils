plugins {
    alias(libs.plugins.kotlinx.binaryCompatibility)
    id("conventions.library")
    id("conventions.publish")
}

publish {
    configurePom(
        artifactId = "annotations",
        pomName = "Metro Utils Annotations",
        pomDescription = "Utility annotations used to generate code using metro-utils",
    )
}

kotlin {
    explicitApi()
}