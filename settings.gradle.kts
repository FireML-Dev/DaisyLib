rootProject.name = "DaisyLib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
            library("inventorygui", "uk.firedev:inventorygui:1.6.3-SNAPSHOT")
            library("anvilgui", "de.md5lukas:anvilgui:2.0.0-SNAPSHOT")
            library("denizen", "com.denizenscript:denizen:1.3.1-SNAPSHOT")
            library("universalscheduler", "com.github.Anon8281:UniversalScheduler:0.1.6")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7.1")
            library("commandapi", "dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.5.3")
            library("vanishchecker", "uk.firedev:VanishChecker:1.1.0")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:2.2.3")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")

            plugin("shadow", "com.gradleup.shadow").version("8.3.0")
            //plugin("paperweight", "io.papermc.paperweight.userdev").version("1.7.1")
            plugin("plugin-yml", "net.minecrell.plugin-yml.paper").version("0.6.0")
        }
    }
}