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

**Note**: No test suite currently exists in this project.

## Project Structure

```
src/
├── main/
│   ├── java/tech/thatgravyboat/creeperoverhaul/
│   │   ├── api/              # Plugin API
│   │   ├── client/           # Client-side code (renderers, UI)
│   │   ├── common/           # Shared code (entities, registry, config)
│   │   ├── mixin/            # Server-side mixins
│   │   └── mixin/client/     # Client-side mixins
│   └── resources/
│       ├── assets/creeperoverhaul/  # Models, textures, animations
│       └── data/                    # Loot tables, recipes
```

## Task Plan → Build Flow

When implementing changes, follow this workflow:

1. **Understand the Task**
   - Review the requirement and identify affected systems (entities, rendering, config, etc.)
   - Search for similar implementations in the codebase to follow existing patterns

2. **Explore the Codebase**
   - Use glob/grep to find relevant files and examples
   - Check existing entity implementations in `common/entity/`
   - Review registry patterns in `common/registry/`

3. **Create Task Plan**
   - Create a task plan file in `plans/` directory (e.g., `plans/implement-feature.md`)
   - List all steps needed to complete the task
   - Update the plan file as work progresses

4. **Implement Changes**
   - Follow code style guidelines (imports, naming, patterns)
   - Use existing utilities (e.g., `Creepers.id()` for resource locations)
   - Add new registries to appropriate `Mod*` classes

5. **Build and Verify**
   ```bash
   # Clean build to catch compilation errors
   ./gradlew clean build
   
   # Run client to test in-game
   ./gradlew runClient
   
   # Run datagen if adding new blocks/items
   ./gradlew runDatagen
   ```

6. **Complete Task**
   - Verify no compilation warnings
   - Test both client and server sides (if applicable)
   - Ensure resource locations are correct
   - Delete the completed plan file from `plans/` directory

## Code Style Guidelines

### Imports
Order: Java standard library → Minecraft/Fabric → Third-party libraries → Project imports

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
- **Max line length**: ~120 characters
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
Always use the utility method:

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
- Log errors using: `CreeperOverhaul.LOGGER`

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
- Fabric API
- Resourceful Lib / Resourceful Config
- GeckoLib (for animations)
- ByteCodecs

## License
All Rights Reserved to Bonsai Studios
