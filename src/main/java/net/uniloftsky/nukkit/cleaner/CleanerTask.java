package net.uniloftsky.nukkit.cleaner;

import cn.nukkit.scheduler.Task;
import net.uniloftsky.nukkit.cleaner.config.CleanerConfig;

/**
 * Cleaner task scheduled to clean up corresponding entities
 */
public final class CleanerTask extends Task {

    /**
     * Plugin instance
     */
    private CleanerPlugin plugin;

    /**
     * Config instance
     */
    private CleanerConfig config;

    public CleanerTask(CleanerPlugin plugin, CleanerConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public void onRun(int interval) {
        plugin.getLogger().info("CleanerTask did something");
    }
}
