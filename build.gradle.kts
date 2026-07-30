allprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")

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

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}