rootProject.name = "DaisyLib"

// Dependencies
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // compileOnly dependencies
            library("paper-api", "io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7.1")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:3.1.0")
            library("jspecify", "org.jspecify:jspecify:1.0.0")

            // Gradle plugins
            plugin("shadow", "com.gradleup.shadow").version("9.0.0")
            plugin("plugin-yml", "de.eldoria.plugin-yml.paper").version("0.9.0")
        }
    }
}

// Modules
include("common")
include("minecraft")
include("messages")