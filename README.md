# Nukkit Cleaner plugin

A plugin for Nukkit that periodically removes dropped items and entities.

# Configuration

This plugin can be configured via the `config.json` file. It contains the following:

```{
  "interval": 1000,
  "scope": {
    "items": true,
    "mobs": true,
    "animals": true,
    "xp": true,
    "projectiles": true
  }
}
```

Where `interval` defines the time interval for clearing entities, specified in milliseconds.<br>
`scope` object includes definition for the types of entities to be cleared. `items` refers to items entities, `mobs`
to mob entities, `animals` to animal entities, `xp` to dropped experience orbs and `projectiles` to existing
projectiles such as arrows.

# What is Nukkit?

Nukkit Server Software (https://cloudburstmc.org/articles/) is used to run a game servers for Minecraft:
Bedrock Edition.
