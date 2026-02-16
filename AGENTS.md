# AGENTS.md - Coding Guidelines for Creeper Overhaul

## Project Overview
- **Type**: Fabric Minecraft Mod (1.21.11)
- **Language**: Java 21+ (compiled to Java 21, CI runs on Java 25)
- **Build Tool**: Gradle with Fabric Loom (1.15-SNAPSHOT)
- **Package**: `tech.thatgravyboat.creeperoverhaul`
- **Mod ID**: `creeperoverhaul`

## Build Commands

```bash
# Build the project (runs datagen automatically)
./gradlew build

# Run client for testing in-game
./gradlew runClient

# Run dedicated data generation
./gradlew runDatagen

# Clean build artifacts
./gradlew clean

# Generate sources JAR
./gradlew sourcesJar

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "ClassName"

# Run a single test method
./gradlew test --tests "ClassName.methodName"
```

Note: No lint command is configured in this project.

## Project Structure

```
src/main/java/tech/thatgravyboat/creeperoverhaul/
├── api/                 # Plugin API (CreeperPlugin, PluginRegistry)
├── client/              # Client-only (renderers, layers, UI)
├── common/              # Shared code
│   ├── block/           # Block implementations
│   ├── config/          # ResourcefulConfig configurations
│   ├── entity/          # Entity classes and goals
│   │   ├── base/        # BaseCreeper, PassiveCreeper, NeutralCreeper, WaterCreeper
│   │   ├── custom/      # Custom creeper variants (e.g., PufferfishCreeper)
│   │   └── goals/       # AI goals (CreeperMeleeAttackGoal, etc.)
│   ├── registry/        # ModItems, ModBlocks, ModEntities, ModSounds
│   └── utils/           # PlatformUtils, Events, AnimationConstants
├── mixin/               # Server-side mixins
├── mixin/client/        # Client-side mixins
└── datagen/             # Data generation (loot tables)
```

## Task Workflow

1. **Understand**: Review requirements, identify affected systems
2. **Explore**: Use glob/grep to find similar implementations
3. **Plan**: Create `plans/implement-feature.md` with steps
4. **Implement**: Follow code style guidelines, use `Creepers.id()` for resources
5. **Verify**: Run `./gradlew clean build` then `./gradlew runClient`
6. **Complete**: Delete plan file, verify no warnings

## Code Style Guidelines

### Imports (order: java → minecraft/fabric → third-party → project)
```java
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
- **Braces**: Same-line opening braces
- **Whitespace**: 1 blank line between methods, 2 between classes

### Naming Conventions
- **Classes**: PascalCase (`Creeper`, `CreeperType`)
- **Methods/Fields**: camelCase (`getTexture()`, `oldSwell`)
- **Constants**: UPPER_SNAKE_CASE
- **Registry holders**: UPPER_SNAKE_CASE (`BLOCKS`, `ENTITIES`)

### Types & Patterns
- Use `record` for immutable data containers
- Use `Optional<T>` instead of null returns
- Use Builder pattern for complex objects
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
Always use `Creepers.id()`:
```java
Creepers.id("textures/entity/jungle/jungle_creeper.png")
```

### Entry Points
- Server: `CreepersFabric.java` - use `@Mod` annotation
- Client: `CreepersClientFabric.java` - client initialization
- Common: `CreeperOverhaul.java` - shared initialization, contains `LOGGER`

### Entity System
Extends Minecraft's `Creeper` and implements GeckoLib's `GeoEntity`:
```java
public class BaseCreeper extends Creeper implements GeoEntity, Shearable {
    public final CreeperType type;
    
    public static EntityType.EntityFactory<BaseCreeper> of(CreeperType type) {
        return (entityType, level) -> new BaseCreeper(entityType, level, type);
    }
}
```

### Mixin Guidelines
- Place in `mixin` or `mixin/client` packages
- Use `@Mixin` with target class
- Use `@Inject` for method injection
- Name invokers descriptively: `LivingEntityRendererInvoker`

### Configuration (ResourcefulConfig)
```java
@Config(value = "creeperoverhaul", categories = {SpawningConfig.class})
@ConfigInfo(title = "Creeper Overhaul", description = "Biome specific creepers")
public final class CreepersConfig {
    @ConfigEntry(id = "destroyBlocks")
    @Comment("Whether creepers destroy blocks.")
    public static boolean destroyBlocks = true;
}
```

### Registry Pattern
```java
public class ModItems {
    public static final RegistryHolder<Item> TINY_CACTUS = ITEMS.register("tiny_cactus", () -> ...);
    
    public static void init() { ITEMS.init(); }
}
```

### Data Generation
Place datagen classes in `datagen/` package. Register providers in Fabric mod entry point.

### Error Handling
- Use `Optional` instead of null
- Early returns to reduce nesting
- Log errors: `CreeperOverhaul.LOGGER`

## CI/CD
- GitHub Actions: `.github/workflows/build.yml`
- Runs on Java 25 (Microsoft distribution)
- Builds on every push and PR

## Dependencies
- **Minecraft**: 1.21.11
- **Fabric Loader**: 0.18.4
- **Fabric Loom**: 1.15-SNAPSHOT
- **Fabric API**: 0.141.3+1.21.11
- **Resourceful Lib/Config**: 3.0.x
- **GeckoLib**: 4.7
- **ByteCodecs**: 1.1.2
