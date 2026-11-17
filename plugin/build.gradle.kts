import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.plugin.yml)
}

dependencies {
    compileOnly(libs.paper.api) {
        version {
            strictly("26.3.build.+")
        }
    }
    compileOnly(libs.placeholderapi)
    compileOnly(libs.vault.api)

    paperLibrary(libs.inventorygui)
    compileOnlyApi(libs.inventorygui)

    paperLibrary(libs.jda)
    compileOnlyApi(libs.jda)

    compileOnly(project(":library"))
}

group = "uk.firedev"
version = project.property("project-version") as String

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

paper {
    name = "DaisyLibPlugin"
    version = project.version.toString()
    main = "uk.firedev.daisylib.DaisyLibPlugin"
    apiVersion = "26.3"
    author = "FireML"
    description = "DaisyLib Plugin"

    loader = "uk.firedev.daisylib.LibraryLoader"
    generateLibrariesJson = true

    serverDependencies {
        register("Vault") {
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
            artifactId = "DaisyLib-Plugin"
            version = project.version.toString()

            from(components["java"])
        }
    }
}

val libraryMain = project(":library")
    .extensions
    .getByType<SourceSetContainer>()["main"]

tasks {
    jar {
        archiveBaseName.set("DaisyLibPlugin")
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        dependsOn(":library:classes")
        from(libraryMain.output)
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    generatePaperPluginDescription {
        useGoogleMavenCentralProxy()
    }
}