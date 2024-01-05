package uk.firedev.daisylib.local;

import org.bukkit.configuration.file.FileConfiguration;
import uk.firedev.daisylib.utils.MessageUtils;

public class LibMessageUtils extends MessageUtils {

    private static LibMessageUtils instance = null;

    public LibMessageUtils(FileConfiguration config) {
        super(config);
        instance = this;
    }

    public static LibMessageUtils getInstance() {
        return instance;
    }

}
