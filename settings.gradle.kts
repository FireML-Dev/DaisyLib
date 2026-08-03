rootProject.name = "DaisyLib"

// Dependencies
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // compileOnly dependencies
            library("paper-api", "io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7.1")

            // Gradle plugins
            plugin("shadow", "com.gradleup.shadow").version("9.0.0")
        }
    }
}