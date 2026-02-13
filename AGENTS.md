# AGENTS.md - Coding Guidelines for Creeper Overhaul

## Project Overview
- **Type**: Fabric Minecraft Mod (1.21.1)
- **Language**: Java 21+
- **Build Tool**: Gradle with Fabric Loom
- **Package**: `tech.thatgravyboat.creeperoverhaul`
- **Mod ID**: `creeperoverhaul`

## Build Commands

```bash
# Build the project
./gradlew build

# Run client for testing
./gradlew runClient

# Run data generation
./gradlew runDatagen

# Clean build artifacts
./gradlew clean

# Generate sources jar
./gradlew sourcesJar
```

**Note**: No test suite currently exists in this project.

## Code Style Guidelines

### Imports
1. **Java standard library** (`java.*`) - first
2. **Minecraft/Vanilla** (`net.minecraft.*`, `net.fabricmc.*`) - second
3. **Third-party mods/libraries** - third
4. **Project imports** (`tech.thatgravyboat.creeperoverhaul.*`) - last

```java
package tech.thatgravyboat.creeperoverhaul.common.entity;

import java.util.Locale;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;
```

### Formatting
- **Indentation**: 4 spaces (no tabs)
- **Line endings**: LF
- **Max line length**: ~120 characters (be reasonable)
- **Braces**: Same-line opening braces for methods/classes
- **Whitespace**: One blank line between methods, two between classes

### Naming Conventions
- **Classes**: PascalCase (e.g., `CreeperType`, `BaseCreeper`)
- **Methods**: camelCase (e.g., `getTexture()`, `build()`)
- **Fields**: camelCase; constants UPPER_SNAKE_CASE
- **Mod ID constant**: `MOD_ID` or `MODID`
- **Registry holders**: UPPER_SNAKE_CASE (e.g., `BLOCKS`, `ENTITIES`)

### Types & Patterns
- Use `record` for immutable data containers
- Use `Optional<T>` for potentially null returns
- Use Builder pattern for complex object construction
- Use `Supplier<T>` and `Function<T, R>` for lazy evaluation

```java
public record CreeperType(
    Function<BaseCreeper, ResourceLocation> texture,
    int melee,
    BooleanSupplier canSpawn
) {
    public Optional<SoundEvent> getDeathSound(BaseCreeper creeper) {
        return Optional.ofNullable(deathSound.apply(creeper));
    }
}
```

### Resource Locations
Always use the utility method for creating resource locations:

```java
public static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
}

// Usage:
Creepers.id("textures/entity/jungle/jungle_creeper.png")
```

### Error Handling
- Use `Optional` instead of returning null
- Use early returns to reduce nesting
- Log errors using the mod's logger: `CreeperOverhaul.LOGGER`

### Mixin Guidelines
- Place mixins in `mixin` or `mixin/client` packages
- Use `@Mixin` annotation with target class
- Use `@Inject` for method injection with proper cancellation
- Name invoker interfaces descriptively (e.g., `LivingEntityRendererInvoker`)

### Configuration
Use ResourcefulConfig annotations for config classes:

```java
@Config(value = "creeperoverhaul", categories = {SpawningConfig.class})
@ConfigInfo(title = "Creeper Overhaul", description = "Biome specific creepers")
@ConfigInfo.Color("#7BB252")
public final class CreepersConfig {
    @ConfigEntry(id = "destroyBlocks")
    @Comment("Changes the Creeper Overhaul creepers to destroy blocks or not.")
    public static boolean destroyBlocks = true;
}
```

### Registry Pattern
Use the project's registry holder pattern:

```java
public class ModItems {
    public static final RegistryHolder<Item> TINY_CACTUS = ITEMS.register("tiny_cactus", ...);
    
    public static void init() {
        ITEMS.init();
    }
}
```

## CI/CD
- GitHub Actions workflow in `.github/workflows/build.yml`
- Runs on Java 25 (Microsoft distribution)
- Builds on every push and PR

## Dependencies
Key dependencies to be aware of:
- Fabric API
- Resourceful Lib / Resourceful Config
- GeckoLib (for animations)
- ByteCodecs

## License
All Rights Reserved to Bonsai Studios
