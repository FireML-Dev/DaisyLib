rootProject.name = "DaisyLib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // compileOnly dependencies
            library("paper-api", "io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "net.milkbowl.vault:VaultUnlockedAPI:2.16")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:3.1.0")

            // implementation dependencies
            library("bstats", "org.bstats:bstats-bukkit:3.1.0")
            library("customblockdata", "com.jeff-media:custom-block-data:2.2.4")
            library("messagelib", "uk.firedev:MessageLib:1.0.4-SNAPSHOT")

            // Loaded via Paper's library loader
            library("nashorn", "org.openjdk.nashorn:nashorn-core:15.6")
            library("vanishchecker", "uk.firedev:VanishChecker:1.0.5")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")
            library("triumphgui", "dev.triumphteam:triumph-gui:3.1.11")
            library("configlib", "uk.firedev:ConfigLib:1.0.1")

            // annotationProcessor dependencies
            library("strokkcommands-annotations", "net.strokkur:commands-annotations:1.5.0")
            library("strokkcommands-processor", "net.strokkur:commands-processor:1.5.0")

            // Gradle plugins
            plugin("shadow", "com.gradleup.shadow").version("9.0.0")
            plugin("plugin-yml", "de.eldoria.plugin-yml.paper").version("0.8.0")
        }
    }
}
