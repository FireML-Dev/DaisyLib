rootProject.name = "DaisyLib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")

            // Requires the remapping shenanigans to work - Compiled against an old Spigot version.
            library("inventorygui", "de.themoep:inventorygui:1.6.4-SNAPSHOT")
            // Does not support MojMap
            library("anvilgui", "net.wesjd:anvilgui:1.10.3-SNAPSHOT")

            library("universalscheduler", "com.github.Anon8281:UniversalScheduler:0.1.6")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7.1")
            library("commandapi", "dev.jorel:commandapi-bukkit-shade:9.6.1")
            library("vanishchecker", "uk.firedev:VanishChecker:1.0.1")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:2.2.3")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")

            plugin("shadow", "com.gradleup.shadow").version("8.3.3")
            //plugin("paperweight", "io.papermc.paperweight.userdev").version("1.7.1")
            plugin("plugin-yml", "net.minecrell.plugin-yml.paper").version("0.6.0")
        }
    }
}