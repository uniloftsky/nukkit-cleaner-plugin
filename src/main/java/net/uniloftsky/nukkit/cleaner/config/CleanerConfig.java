package net.uniloftsky.nukkit.cleaner.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.uniloftsky.nukkit.cleaner.CleanerPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class to hold configurable data such a cleaning interval, types of entities that should be cleaned, etc.
 */
public final class CleanerConfig {

    /**
     * Name of main config
     */
    private static final String MAIN_CONFIG = "config.json";

    /**
     * Plugin instance
     */
    private CleanerPlugin plugin;

    /**
     * Gson to parse JSON configs
     */
    private Gson gson;

    /**
     * Cleaning interval. Read from config
     */
    private int interval;

    /**
     * Cleaning scope. Holds information what entities should be cleaned and what not
     */
    private CleanerConfigScope cleaningScope;

    /**
     * Flag to define if config is initialized. After successful initialization the value changes to true
     */
    private boolean configInitialized = false;

    public CleanerConfig(CleanerPlugin plugin) {
        this.plugin = plugin;
        this.gson = new Gson();
    }

    /**
     * Initialization method. Must be invoked after instantiation before calling any further method
     *
     * @return true if initialization was successful, false if wasn't
     */
    public boolean init() {
        if (!configInitialized) {
            plugin.getLogger().info("Loading configuration...");
            plugin.getLogger().info("Loading " + MAIN_CONFIG + "...");
            plugin.saveResource(MAIN_CONFIG);

            String mainConfigContents;
            try {
                mainConfigContents = getConfigContents();
            } catch (IOException ex) {
                plugin.getLogger().error("Cannot get " + MAIN_CONFIG + " file");
                return false;
            }

            JsonObject configObject = JsonParser.parseString(mainConfigContents).getAsJsonObject();
            JsonElement intervalElement = configObject.get(ConfigFields.INTERVAL_FIELD); // retrieving interval from config
            if (intervalElement != null) {
                this.interval = intervalElement.getAsInt();
            } else {
                plugin.getLogger().error("Cannot initialize " + MAIN_CONFIG + ". No field: " + ConfigFields.INTERVAL_FIELD);
                return false;
            }

            JsonElement scopeElement = configObject.get(ConfigFields.SCOPE_FIELD); // retrieving cleaning scope from config
            if (scopeElement != null) {
                this.cleaningScope = gson.fromJson(scopeElement, CleanerConfigScope.class);
            } else {
                plugin.getLogger().error("Cannot initialize " + MAIN_CONFIG + ". No field: " + ConfigFields.SCOPE_FIELD);
                return false;
            }

            configInitialized = true;
            plugin.getLogger().info("Configuration initialized with the following options: " + this.toString());
        }
        return true;
    }

    public int getInterval() {
        isInitialized();
        return this.interval;
    }

    public boolean isCleanItems() {
        isInitialized();
        return this.cleaningScope.isItems();
    }

    public boolean isCleanMobs() {
        isInitialized();
        return this.cleaningScope.isMobs();
    }

    public boolean isCleanAnimals() {
        isInitialized();
        return this.cleaningScope.isAnimals();
    }

    public boolean isCleanXp() {
        isInitialized();
        return this.cleaningScope.isXp();
    }

    public boolean isCleanProjectiles() {
        isInitialized();
        return this.cleaningScope.isProjectiles();
    }

    void isInitialized() {
        if (!configInitialized) {
            throw new RuntimeException("Config wasn't initialized. Further actions are not allowed");
        }
    }

    String getConfigContents() throws IOException {
        Path configPath = plugin.getDataFolder().toPath().resolve(MAIN_CONFIG);
        return new String(Files.readAllBytes(configPath));
    }

    static class ConfigFields {

        static final String INTERVAL_FIELD = "interval";
        static final String SCOPE_FIELD = "scope";

    }

    @Override
    public String toString() {
        return "{" +
                "interval=" + interval +
                ", scope=" + cleaningScope +
                '}';
    }
}
