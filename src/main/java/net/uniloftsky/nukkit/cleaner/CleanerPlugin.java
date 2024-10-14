package net.uniloftsky.nukkit.cleaner;

import cn.nukkit.plugin.PluginBase;
import net.uniloftsky.nukkit.cleaner.config.CleanerConfig;

/**
 * Main plugin class
 */
public class CleanerPlugin extends PluginBase {

    /**
     * Default Ticks Per Second value
     */
    private static final int DEFAULT_TPS = 20;

    private static CleanerPlugin INSTANCE;

    public static CleanerPlugin getInstance() {
        return INSTANCE;
    }

    /**
     * Invoked on plugin enabling, when server starts
     */
    @Override
    public void onEnable() {
        CleanerConfig config = new CleanerConfig(this);
        boolean configInitialized = config.init();
        if (!configInitialized) {
            this.getLogger().error("Configuration cannot be initialized. Plugin will be disabled");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // starting the cleaner task
        int period = convertMillisecondsToTicks(config.getInterval());
        this.getServer().getScheduler().scheduleRepeatingTask(new CleanerTask(this, config), period);
        this.getLogger().info("CleanerPlugin enabled!");
    }

    /**
     * Invoked on plugin disabling, when server stops
     */
    @Override
    public void onDisable() {
        this.getLogger().info("CleanerPlugin disabled!");
    }

    /**
     * Nukkit scheduler period should be defined in ticks. One second = 20 Ticks
     *
     * @param milliseconds milliseconds to convert
     * @return ticks
     */
    int convertMillisecondsToTicks(long milliseconds) {
        return (int) (milliseconds / 1000 * DEFAULT_TPS);
    }
}
