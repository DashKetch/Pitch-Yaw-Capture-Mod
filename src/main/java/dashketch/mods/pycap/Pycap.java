package dashketch.mods.pycap;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Pycap implements ModInitializer {

    public static final Logger log = LoggerFactory.getLogger(Pycap.class);
    public static PycapConfig config = PycapConfig.load();

    @Override
    public void onInitialize() {
        log.info("Pycap Mod has loaded!");

        if (config.showMessages) {
            log.info("Config Loaded!");
        } else  {
            log.warn("Config Not Loaded!");
        }
    }
}
