package net.uniloftsky.nukkit.cleaner.config;

/**
 * Cleaner scope defines what type of entity should be cleaned
 */
public final class CleanerConfigScope {

    /**
     * Defines if dropped items should be cleaned
     */
    private final boolean items;

    /**
     * Defines of existing mobs should be cleaned
     */
    private final boolean mobs;

    /**
     * Defines if existing animals should be cleaned
     */
    private final boolean animals;

    /**
     * Defines if dropped XP orbs should be cleaned
     */
    private final boolean xp;

    /**
     * Defines if existing projectiles (for example arrows) should be cleaned
     */
    private final boolean projectiles;

    public CleanerConfigScope(boolean items, boolean mobs, boolean animals, boolean xp, boolean projectiles) {
        this.items = items;
        this.mobs = mobs;
        this.animals = animals;
        this.xp = xp;
        this.projectiles = projectiles;
    }

    boolean isItems() {
        return items;
    }

    boolean isMobs() {
        return mobs;
    }

    boolean isAnimals() {
        return animals;
    }

    boolean isXp() {
        return xp;
    }

    boolean isProjectiles() {
        return projectiles;
    }

    @Override
    public String toString() {
        return "{" +
                "items=" + items +
                ", mobs=" + mobs +
                ", animals=" + animals +
                ", xp=" + xp +
                ", projectiles=" + projectiles +
                '}';
    }
}
