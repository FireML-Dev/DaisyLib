import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
}

repositories {
    mavenLocal()
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
    compileOnly(libs.vault) {
        exclude("*", "*")
    }
    compileOnly(libs.miniplaceholders)

    implementation(libs.triumphgui)
    implementation(libs.commandapi)
    implementation(libs.vanishchecker)
    implementation(libs.boostedyaml)
    implementation(libs.bstats)
    implementation(libs.customblockdata)

    compileOnlyApi(libs.triumphgui)
    paperLibrary(libs.triumphgui)

    compileOnlyApi(libs.boostedyaml)
    paperLibrary(libs.boostedyaml)

    compileOnlyApi(libs.customblockdata)
    paperLibrary(libs.customblockdata)

    compileOnlyApi(libs.messagelib)
    paperLibrary(libs.messagelib)

    compileOnlyApi(libs.vanishchecker)
    paperLibrary(libs.vanishchecker)

    paperLibrary(libs.nashorn)
}

group = "uk.firedev"
version = properties["project-version"] as String
description = "A collection of classes for easier use of Paper's API"
java.sourceCompatibility = JavaVersion.VERSION_21

paper {
    name = rootProject.name
    version = project.version.toString()
    main = "uk.firedev.daisylib.local.DaisyLib"
    apiVersion = "1.21.8"
    author = "FireML"
    description = project.description.toString()

    loader = "uk.firedev.daisylib.local.LibraryLoader"
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
        relocate("dev.jorel.commandapi", "uk.firedev.daisylib.libs.commandapi")
        relocate("org.bstats", "uk.firedev.daisylib.libs.bstats")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
