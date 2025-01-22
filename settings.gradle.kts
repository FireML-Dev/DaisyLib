rootProject.name = "DaisyLib"

include(":daisylib-api")
include(":daisylib-plugin")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7")
            library("commandapi", "dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.7.1-SNAPSHOT")
            library("vanishchecker", "uk.firedev:VanishChecker:1.0.4")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:2.2.3")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")
            library("bstats", "org.bstats:bstats-bukkit:3.1.0")
            library("inventorygui", "uk.firedev:inventorygui:1.6.4-1.21.4-SNAPSHOT")
            // TODO update this to 2.0.1 after my PR is merged
            library("anvilgui", "de.md5lukas:anvilgui:2.0.0")

            plugin("shadow", "com.gradleup.shadow").version("8.3.5")
            //plugin("paperweight", "io.papermc.paperweight.userdev").version("1.7.1")
            plugin("plugin-yml", "net.minecrell.plugin-yml.paper").version("0.6.0")
        }
    }
}
