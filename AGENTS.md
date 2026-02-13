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

# Run all tests (if tests exist)
./gradlew test

# Run a single test class
./gradlew test --tests "ClassName"

# Run a single test method
./gradlew test --tests "ClassName.methodName"
```

## Project Structure

```
src/main/java/tech/thatgravyboat/creeperoverhaul/
├── api/              # Plugin API
├── client/           # Client-side code (renderers, UI)
├── common/           # Shared code (entities, registry, config)
├── mixin/            # Server-side mixins
└── mixin/client/     # Client-side mixins
```

## Version Catalog

Dependencies are managed via Gradle version catalog in `gradle/libs.versions.toml`:

```toml
[versions]
minecraft = "1.21.1"
fabric-loom = "1.15-SNAPSHOT"
fabric-api = "0.116.8+1.21.1"
geckolib = "4.7"

[libraries]
minecraft = { module = "com.mojang:minecraft", version.ref = "minecraft" }
geckolib = { module = "software.bernie.geckolib:geckolib-fabric-1.21.1", version.ref = "geckolib" }

[plugins]
fabric-loom = { id = "net.fabricmc.fabric-loom-remap", version.ref = "fabric-loom" }
```

Usage in `build.gradle`:
```groovy
plugins {
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft)
    modImplementation(libs.fabric.api)
    modImplementation(libs.geckolib)
}
```

## Code Style Guidelines

### Imports
Order: Java stdlib → Minecraft/Fabric → Third-party → Project

```java
package tech.thatgravyboat.creeperoverhaul.common.entity;

import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;
```

### Formatting
- **Indentation**: 4 spaces
- **Line endings**: LF
- **Max line length**: ~120 chars
- **Braces**: Same-line opening braces
- **Whitespace**: 1 blank line between methods, 2 between classes

### Naming Conventions
- **Classes**: PascalCase (e.g., `CreeperType`, `BaseCreeper`)
- **Methods/Fields**: camelCase
- **Constants**: UPPER_SNAKE_CASE
- **Registry holders**: UPPER_SNAKE_CASE (e.g., `BLOCKS`, `ENTITIES`)

### Types & Patterns
- Use `record` for immutable data containers
- Use `Optional<T>` instead of null returns
- Use Builder pattern for complex construction
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
Always use the utility method:
```java
Creepers.id("textures/entity/jungle/jungle_creeper.png")
```

### Error Handling
- Use `Optional` instead of null
- Use early returns to reduce nesting
- Log errors via: `CreeperOverhaul.LOGGER`

### Registry Pattern
```java
public class ModItems {
    public static final RegistryHolder<Item> TINY_CACTUS = ITEMS.register("tiny_cactus", ...);
    
    public static void init() {
        ITEMS.init();
    }
}
```

### Mixin Guidelines
- Place mixins in `mixin` or `mixin/client` packages
- Use `@Mixin` annotation with target class
- Use `@Inject` for method injection with proper cancellation
- Name invoker interfaces descriptively (e.g., `LivingEntityRendererInvoker`)

### Configuration
Use ResourcefulConfig annotations:
```java
@Config(value = "creeperoverhaul", categories = {SpawningConfig.class})
@ConfigInfo(title = "Creeper Overhaul", description = "Biome specific creepers")
public final class CreepersConfig {
    @ConfigEntry(id = "destroyBlocks")
    @Comment("Changes the Creeper Overhaul creepers to destroy blocks or not.")
    public static boolean destroyBlocks = true;
}
```

## Task Workflow

1. Explore codebase - find similar implementations
2. Create plan in `plans/` directory
3. Implement changes following code style
4. Build and verify: `./gradlew clean build`
5. Test in-game: `./gradlew runClient`
6. Run datagen if needed: `./gradlew runDatagen`
7. Delete completed plan file

## CI/CD
- GitHub Actions runs on Java 25 (Microsoft)
- Builds on every push and PR

## Dependencies
- Fabric API, Fabric Loader
- Resourceful Lib / Resourceful Config
- GeckoLib (animations)
- ByteCodecs
