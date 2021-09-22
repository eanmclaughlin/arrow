package io.github.nomisrev

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownTaskException
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

/**
 * An empty [Plugin] that exposes a set of utility functions to simplify setting up MPP publishing.
 */
class MppPublishingPlugin : Plugin<Project> {
  override fun apply(target: Project) {}
}

fun Project.setupPublishing(
  pomDevId: String,
  pomDevName: String,
  projectUrl: String,
  projectDesc: String,
  releaseRepo: java.net.URI,
  snapshotRepo: java.net.URI,
  sonatypeUsername: String? = System.getenv("SONATYPE_USER"),
  sonatypePassword: String? = System.getenv("SONATYPE_PWD"),
  pass: String? = System.getenv("SIGNINGPASSWORD"),
  key: String? = System.getenv("SIGNINGKEY"),
  licenseName: String = "The Apache Software License, Version 2.0",
  licenseUrl: String = "https://www.apache.org/licenses/LICENSE-2.0.txt",
  licenseDist: String = "repo",
  scmConnection: String = "${projectUrl.replaceFirst("https://", "scm:git:git://")}.git",
  scmDevConnection: String = "${projectUrl.replaceFirst("https://", "scm:git:ssh://")}.git",
) {
  afterEvaluate {
    extensions.getByType(PublishingExtension::class.java).apply {
      val mavenPublications = publications.withType<MavenPublication>()
      mavenPublications.all {
        artifact(project.javadocJar())
        setupPom(
          url = projectUrl,
          description = projectDesc,
          pomDevId = pomDevId,
          pomDevName = pomDevName,
          licenseName = licenseName,
          licenseUrl = licenseUrl,
          licenseDist = licenseDist,
          scmConnection = scmConnection,
          scmDevConnection = scmDevConnection
        )
      }
      repositories {
        maven(
          if (version.toString().endsWith("SNAPSHOT")) snapshotRepo else releaseRepo,
          sonatypeUsername,
          sonatypePassword
        )
      }
      Nullable.zip(key, pass) { key, pass -> signPublications(key, pass) }
    }
  }
}

object Nullable {
  fun <A : Any, B : Any, C : Any> zip(a: A?, b: B?, f: (A, B) -> C?): C? =
    a?.let { aa ->
      b?.let { bb -> f(aa, bb) }
    }
}

fun Project.signPublications(
  key: String,
  pass: String
) {
  val publications = project
    .extensions
    .getByType(PublishingExtension::class.java)
    .publications

  project
    .extensions
    .getByType(SigningExtension::class.java)
    .apply {
      useInMemoryPgpKeys(key, pass)
    }.sign(publications)
}

/* We either try to find the existing javadocJar, or we register an empty javadocJar task */
fun Project.javadocJar(): TaskProvider<Jar> {
  val taskName = "javadocJar"
  return try {
    tasks.named(name = taskName)
  } catch (e: UnknownTaskException) {
    tasks.register(name = taskName) {
      archiveClassifier by "javadoc"
    }
  }
}

fun MavenPublication.setupPom(
  url: String,
  description: String,
  pomDevId: String,
  pomDevName: String,
  licenseName: String,
  licenseUrl: String,
  licenseDist: String,
  scmConnection: String,
  scmDevConnection: String,
) {
  pom {
    if (!name.isPresent) {
      name by artifactId
    }
    this@pom.description.set(description)
    this@pom.url.set(url)
    licenses {
      license {
        name by licenseName
        this@license.url by licenseUrl
        this@license.distribution by licenseDist
      }
    }
    developers {
      developer {
        id by pomDevId
        name by pomDevName
      }
    }
    scm {
      connection by scmConnection
      developerConnection by scmDevConnection
      this@scm.url by url
    }
    if (url.startsWith("https://github.com")) issueManagement {
      system by "GitHub"
      this@issueManagement.url by "$url/issues"
    }
  }
}

fun RepositoryHandler.maven(
  uri: java.net.URI,
  sonatypeUsername: String?,
  sonatypePassword: String?,
): MavenArtifactRepository = maven {
  name = "Maven"
  url = uri
  credentials {
    username = sonatypeUsername
    password = sonatypePassword
  }
}
