allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://repo.codemc.io/repository/FireML/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.minebench.de/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://maven.citizensnpcs.co/repo/")

        // This should always be last because it likes to act up.
        maven("https://jitpack.io")
    }
    plugins.withId("java") {
        extensions.configure<JavaPluginExtension> {
            withSourcesJar()
            withJavadocJar()
        }

        tasks.withType<Javadoc>().configureEach {
            isFailOnError = false
            (options as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
        }
    }
}