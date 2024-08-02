plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://maven.citizensnpcs.co/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.firedev.uk/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.denizen)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault)
    compileOnly(libs.miniplaceholders)

    implementation(libs.inventorygui)
    implementation(libs.anvilgui)
    implementation(libs.universalscheduler)
    implementation(libs.commandapi)
    implementation(libs.vanishchecker)
}

group = "uk.firedev"
version = "2.0.2-SNAPSHOT"
description = "A collection of classes for easier use of Paper's API"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    repositories {
        maven {
            name = "firedevRepo"
            url = uri("https://repo.firedev.uk/repository/maven-snapshots/")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "uk.firedev"
            artifactId = "DaisyLib"
            version = version

            from(components["java"])
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)

        outputs.file("libs/DaisyLib-${version}.jar")

        doLast {
            val buildDir = project.layout.buildDirectory

            val unwantedFile = buildDir.file("libs/DaisyLib-${version}-dev.jar").get().asFile
            if (unwantedFile.exists()) {
                unwantedFile.delete()
            }

            val oldFile = buildDir.file("libs/DaisyLib-${version}-dev-all.jar").get().asFile
            val newFile = buildDir.file("libs/DaisyLib-${version}.jar").get().asFile

            if (oldFile.exists()) {
                oldFile.renameTo(newFile)
            }
        }
    }
    shadowJar {

        archiveBaseName.set("DaisyLib")
        archiveVersion.set(version.toString())
        archiveClassifier.set("")

        relocate("de.themoep.inventorygui", "uk.firedev.daisylib.libs.themoep.inventorygui")
        relocate("net.wesjd.anvilgui", "uk.firedev.daisylib.libs.wesjd.anvilgui")
        relocate("com.github.Anon8281.universalScheduler", "uk.firedev.daisylib.libs.Anon8281.universalScheduler")
        relocate("dev.jorel.commandapi", "uk.firedev.daisylib.libs.commandapi")
        relocate("uk.firedev.vanishchecker", "uk.firedev.daisylib.utils.vanishchecker")

        manifest {
            attributes["paperweight-mappings-namespace"] = "spigot"
        }
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
