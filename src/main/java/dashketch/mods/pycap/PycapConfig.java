package dashketch.mods.pycap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PycapConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("pycap.json").toFile();

    // The setting we want to save
    public boolean showMessages = true;

    public static PycapConfig load() {
        PycapConfig config;
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, PycapConfig.class);
            } catch (IOException e) {
                System.err.println("[Pycap] Failed to load config, using defaults.");
                config = new PycapConfig();
            }
        } else {
            config = new PycapConfig();
            config.save(); // <--- ADD THIS: Create the file if it doesn't exist
        }
        return config;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("[Pycap] Failed to save config.");
        }
    }
}