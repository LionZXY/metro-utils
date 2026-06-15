package uk.kulikov.convention

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.the
import javax.inject.Inject

open class PublishConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("publish", PublishExtension::class.java)

        target.plugins.apply(target.libs.plugins.mavenPublish.get().pluginId)
        // NOTE: Dokka 1.9.20 cannot run on JDK 21+/25 (it fails parsing the JVM version, e.g.
        // "java.lang.IllegalArgumentException: 25.0.3"). The javadoc jar therefore uses
        // JavadocJar.Empty() below instead of Dokka so publishing works on modern JDKs. Re-enable
        // Dokka here (and switch JavadocJar back to Dokka) once the project migrates to Dokka 2.x.

        val mavenPublishing = target.extensions
            .getByType(MavenPublishBaseExtension::class.java)

        @Suppress("UnstableApiUsage")
        mavenPublishing.pomFromGradleProperties()
        if (!target.gradle.startParameter.taskNames.any { it.contains("publishToMavenLocal") }) {
            mavenPublishing.signAllPublications()
        }
        mavenPublishing.publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

        target.plugins.withId("org.jetbrains.kotlin.jvm") {
            mavenPublishing.configure(
                platform = KotlinJvm(
                    javadocJar = JavadocJar.Empty(),
                    sourcesJar = true,
                ),
            )
        }

//    // Fixes issues like:
//    // Task 'generateMetadataFileForMavenPublication' uses this output of task 'dokkaJavadocJar'
//    // without declaring an explicit or implicit dependency.
//    target.tasks.withType(GenerateModuleMetadata::class.java).configureEach {
//      it.mustRunAfter(target.tasks.withType(Jar::class.java))
//    }
    }
}

open class PublishExtension @Inject constructor(
    private val target: Project,
) {
    fun configurePom(
        artifactId: String,
        pomName: String,
        pomDescription: String,
    ) {
        target.the<PublishingExtension>()
            .publications.withType(MavenPublication::class.java)
            .configureEach {
                this.artifactId = artifactId

                pom {
                    name.set(pomName)
                    description.set(pomDescription)
                }
            }
    }
}
