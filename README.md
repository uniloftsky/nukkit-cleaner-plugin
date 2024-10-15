# Nukkit Cleaner plugin

Plugin for Nukkit that removes dropped items, entities periodically.

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

Where `interval` defines the interval wherein entities are cleared. Should be specified in milliseconds.<br>
`scope` object includes definition for what exact types of entities would be cleared. `items` for items entities, `mobs` for mobs entities, `animals` for animal entities, `xp` for dropped experience orbs, `projectiles` for existing projectiles such as arrows

# What is Nukkit?

Nukkit Server Software (https://cloudburstmc.org/articles/) is used to run a game servers for Minecraft:
Bedrock Edition.
