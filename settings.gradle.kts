rootProject.name = "DaisyLib"

include(":daisylib-api")
include(":daisylib-plugin")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Paper API
            library("paper-api", "io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

            // compileOnly dependencies
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:2.2.3")
            library("nexo", "com.nexomc:nexo:0.7.0")
            library("denizen", "com.denizenscript:denizen:1.3.1-SNAPSHOT")

            // implementation dependencies
            library("triumphgui", "dev.triumphteam:triumph-gui:3.1.11")
            library("anvilgui", "de.md5lukas:anvilgui:2.0.0")
            library("commandapi", "dev.jorel:commandapi-bukkit-shade-mojang-mapped:10.0.0")
            library("vanishchecker", "uk.firedev:VanishChecker:1.0.5")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")
            library("bstats", "org.bstats:bstats-bukkit:3.1.0")
            library("customblockdata", "com.jeff-media:custom-block-data:2.2.4")

            // Loaded via Paper's library loader
            library("nashorn", "org.openjdk.nashorn:nashorn-core:15.6")

            // Gradle plugins
            plugin("shadow", "com.gradleup.shadow").version("8.3.5")
            plugin("plugin-yml", "de.eldoria.plugin-yml.paper").version("0.7.1")
        }
    }
}
