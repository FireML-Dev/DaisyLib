plugins {
    `java-library`
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.WARN
    from(project(":core").sourceSets.main.get().output)
    from(project(":messages").sourceSets.main.get().output)
    from(project(":database").sourceSets.main.get().output)
}

allprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")

    group = "uk.firedev.daisylib"
    version = project.property("project-version") as String

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://repo.codemc.io/repository/FireML/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.minebench.de/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")

        // This should always be last because it likes to act up.
        maven("https://jitpack.io")
    }

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                url = uri("https://repo.codemc.io/repository/FireML/")

                val mavenUsername = System.getenv("JENKINS_USERNAME")
                val mavenPassword = System.getenv("JENKINS_PASSWORD")

                if (mavenUsername != null && mavenPassword != null) {
                    credentials {
                        username = mavenUsername
                        password = mavenPassword
                    }
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                val root = rootProject.name
                val id = if (project.name == "DaisyLib") {
                    root
                } else {
                    root + "-" + project.name
                }

                groupId = project.group.toString()
                artifactId = id
                version = project.version.toString()

                artifact(tasks.named("jar"))
            }
        }
    }
}