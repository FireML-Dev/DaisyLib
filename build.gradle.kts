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
    maven("https://maven.citizensnpcs.co/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.firedev.uk/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.md5lukas.de/public/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault)
    compileOnly(libs.miniplaceholders)

    implementation(libs.inventorygui)
    implementation(libs.anvilgui)
    implementation(libs.universalscheduler)
    implementation(libs.commandapi)
    implementation(libs.vanishchecker)
    implementation(libs.boostedyaml)
}

group = "uk.firedev"
version = "2.0.3-SNAPSHOT"
description = "A collection of classes for easier use of Paper's API"
java.sourceCompatibility = JavaVersion.VERSION_21

paper {
    name = project.name
    version = project.version.toString()
    main = "uk.firedev.daisylib.local.DaisyLib"
    apiVersion = "1.21"
    author = "FireML"
    description = project.description.toString()
    foliaSupported = true

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
    }
}

publishing {
    repositories {
        maven {
            name = "firedevRepo"

            // Repository settings
            var repoUrlString = "https://repo.firedev.uk/repository/maven-"
            repoUrlString += if (project.version.toString().endsWith("-SNAPSHOT")) {
                "snapshots/"
            } else {
                "releases/"
            }
            url = uri(repoUrlString)

            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
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

        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        relocate("de.themoep.inventorygui", "uk.firedev.daisylib.libs.themoep.inventorygui")
        relocate("net.wesjd.anvilgui", "uk.firedev.daisylib.libs.wesjd.anvilgui")
        relocate("com.github.Anon8281.universalScheduler", "uk.firedev.daisylib.libs.Anon8281.universalScheduler")
        relocate("dev.jorel.commandapi", "uk.firedev.daisylib.libs.commandapi")
        relocate("dev.dejvokep.boostedyaml", "uk.firedev.daisylib.libs.boostedyaml")
        relocate("uk.firedev.vanishchecker", "uk.firedev.daisylib.utils.vanishchecker")

        //manifest {
        //    attributes["paperweight-mappings-namespace"] = "spigot"
        //}
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
