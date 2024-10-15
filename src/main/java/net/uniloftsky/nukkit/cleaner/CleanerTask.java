package net.uniloftsky.nukkit.cleaner;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.item.EntityXPOrb;
import cn.nukkit.entity.mob.EntityFlyingMob;
import cn.nukkit.entity.mob.EntitySwimmingMob;
import cn.nukkit.entity.mob.EntityWalkingMob;
import cn.nukkit.entity.passive.EntityFlyingAnimal;
import cn.nukkit.entity.passive.EntityWalkingAnimal;
import cn.nukkit.entity.passive.EntityWaterAnimal;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import net.uniloftsky.nukkit.cleaner.config.CleanerConfig;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Entity clean index. Key - type of entity to be cleaned, value - flag if entity should be cleaned
     */
    private Map<Class<? extends Entity>, Boolean> entityCleanIndex;

    public CleanerTask(CleanerPlugin plugin, CleanerConfig config) {
        this.plugin = plugin;
        this.config = config;

        // initializing entity clean index
        entityCleanIndex = new HashMap<>();

        // items
        entityCleanIndex.put(EntityItem.class, config.isCleanItems());

        // animals
        entityCleanIndex.put(EntityWalkingAnimal.class, config.isCleanAnimals());
        entityCleanIndex.put(EntityWaterAnimal.class, config.isCleanAnimals());
        entityCleanIndex.put(EntityFlyingAnimal.class, config.isCleanAnimals());

        // mobs
        entityCleanIndex.put(EntityWalkingMob.class, config.isCleanMobs());
        entityCleanIndex.put(EntityFlyingMob.class, config.isCleanMobs());
        entityCleanIndex.put(EntitySwimmingMob.class, config.isCleanMobs());

        // xp orbs
        entityCleanIndex.put(EntityXPOrb.class, config.isCleanXp());

        // projectiles
        entityCleanIndex.put(EntityProjectile.class, config.isCleanProjectiles());
    }

    @Override
    public void onRun(int interval) {
        plugin.getLogger().info("Starting the cleaning...");

        Map<Integer, Level> levels = plugin.getServer().getLevels();
        for (Level level : levels.values()) {
            plugin.getLogger().info("Cleaning the level: " + level.getName());
            for (Entity entity : level.getEntities()) {
                entityCleanIndex.forEach((cleanableEntity, shouldClean) -> {
                    if (shouldClean && cleanableEntity.isInstance(entity)) {
                        entity.despawnFromAll();
                    }
                });
            }
            level.unloadChunks();
            plugin.getLogger().info("Successfully cleaned the level: " + level.getName());
        }
    }
}
