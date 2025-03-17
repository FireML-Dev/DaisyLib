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
    maven("https://maven.citizensnpcs.co/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.firedev.uk/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.minebench.de/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.md5lukas.de/public")

    // ItemAddons
    maven("https://maven.citizensnpcs.co/repo")
    maven("https://repo.nexomc.com/releases")
}

dependencies {
    api(project(":daisylib-api"))

    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault) {
        exclude("*", "*")
    }
    compileOnly(libs.miniplaceholders)

    // ItemAddons
    compileOnly(libs.denizen) {
        exclude("*", "*")
    }
    compileOnly(libs.nexo)

    implementation(libs.triumphgui)
    implementation(libs.anvilgui)
    implementation(libs.commandapi)
    implementation(libs.vanishchecker)
    implementation(libs.boostedyaml)
    implementation(libs.bstats)
    implementation(libs.customblockdata)

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
    apiVersion = "1.21.4"
    author = "FireML"
    description = project.description.toString()

    loader = "uk.firedev.daisylib.local.LibraryLoader"
    generateLibrariesJson = true

    serverDependencies {
        register("Vault") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
        register("Denizen") {
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

            from(components["shadow"])
        }
    }
}

tasks {
    jar {
        doLast {
            val jarFile = archiveFile.get().asFile
            jarFile.delete()
        }
    }
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        // Libs Package
        relocate("dev.triumphteam.gui", "uk.firedev.daisylib.libs.triumphgui")
        relocate("net.wesjd.anvilgui", "uk.firedev.daisylib.libs.wesjd.anvilgui")
        relocate("dev.jorel.commandapi", "uk.firedev.daisylib.libs.commandapi")
        relocate("dev.dejvokep.boostedyaml", "uk.firedev.daisylib.libs.boostedyaml")
        relocate("org.bstats", "uk.firedev.daisylib.libs.bstats")
        relocate("com.jeff_media.customblockdata", "uk.firedev.daisylib.libs.customblockdata")

        // Utils Package
        relocate("uk.firedev.vanishchecker", "uk.firedev.daisylib.utils.vanishchecker")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
