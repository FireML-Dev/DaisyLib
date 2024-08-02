rootProject.name = "DaisyLib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("paper-api", "1.21-R0.1-SNAPSHOT")
            library("paper-api", "io.papermc.paper", "paper-api").versionRef("paper-api")
            library("inventorygui", "uk.firedev:inventorygui:1.6.3-SNAPSHOT")
            library("anvilgui", "net.wesjd:anvilgui:1.9.6-SNAPSHOT")
            library("denizen", "com.denizenscript:denizen:1.3.1-SNAPSHOT")
            library("universalscheduler", "com.github.Anon8281:UniversalScheduler:0.1.6")
            library("placeholderapi", "me.clip:placeholderapi:2.11.6")
            library("vault", "com.github.MilkBowl:VaultAPI:1.7.1")
            library("commandapi", "dev.jorel:commandapi-bukkit-shade:9.5.1")
            library("vanishchecker", "uk.firedev:VanishChecker:1.1.0")
            library("miniplaceholders", "io.github.miniplaceholders:miniplaceholders-api:2.2.3")

            plugin("shadow", "io.github.goooler.shadow").version("8.1.8")
            plugin("paperweight", "io.papermc.paperweight.userdev").version("1.7.1")
            plugin("plugin-yml", "net.minecrell.plugin-yml.paper").version("0.6.0")
        }
    }
}