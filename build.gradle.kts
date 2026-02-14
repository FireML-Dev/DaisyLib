import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.io/repository/FireML/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.minebench.de/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault)
    compileOnly(libs.miniplaceholders)

    implementation(libs.bstats)
    implementation(libs.customblockdata)
    implementation(libs.messagelib)
    implementation(libs.configlib)
    implementation(libs.triumphgui)

    compileOnlyApi(libs.boostedyaml)
    paperLibrary(libs.boostedyaml)

    compileOnlyApi(libs.vanishchecker)
    paperLibrary(libs.vanishchecker)

    paperLibrary(libs.nashorn)
}

group = "uk.firedev"
version = properties["project-version"] as String
description = "A collection of classes for easier use of Paper's API"
java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
    withJavadocJar()
}

paper {
    name = rootProject.name
    version = project.version.toString()
    main = "uk.firedev.daisylib.internal.DaisyLibPlugin"
    apiVersion = "1.21.11"
    author = "FireML"
    description = project.description.toString()

    loader = "uk.firedev.daisylib.internal.LibraryLoader"
    generateLibrariesJson = true

    serverDependencies {
        register("Vault") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
        register("PlaceholderAPI") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
        register("MiniPlaceholders") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
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

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        // Libs Package
        relocate("org.bstats", "uk.firedev.daisylib.libs.bstats")
        relocate("com.jeff_media.customblockdata", "uk.firedev.daisylib.libs.customblockdata")
        relocate("uk.firedev.messagelib", "uk.firedev.daisylib.libs.messagelib")
        relocate("uk.firedev.configlib", "uk.firedev.daisylib.libs.configlib")
        relocate("dev.triumphteam.gui", "uk.firedev.daisylib.libs.triumphgui")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    generatePaperPluginDescription {
        useGoogleMavenCentralProxy()
    }
    javadoc {
        // Don't fail when missing Javadoc comments
        isFailOnError = false
        // Disable warnings about missing Javadoc comments
        (options as CoreJavadocOptions).apply {
            addBooleanOption("Xdoclint:none", true)
        }
    }
}
