plugins {
    `java-library`
    `maven-publish`
}

group = "uk.firedev"
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

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
    withJavadocJar()
}

publishing {
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
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}

tasks.javadoc {
    // Don't fail when missing Javadoc comments
    isFailOnError = false
    // Disable warnings about missing Javadoc comments
    (options as CoreJavadocOptions).apply {
        addBooleanOption("Xdoclint:none", true)
    }
}