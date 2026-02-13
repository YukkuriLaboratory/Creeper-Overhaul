# Fabric DataGen Migration Plan: Loot Tables

## Overview

This plan details the migration of loot table JSON files from static resources to Fabric DataGen providers. The project already has the DataGen infrastructure set up but the providers are not fully implemented or registered.

## Current State

### Existing Loot Tables (JSON)
- **Location**: `src/main/resources/data/creeperoverhaul/loot_table/`
- **Block Loot Tables**: 2 files
  - `blocks/tiny_cactus.json`
  - `blocks/potted_tiny_cactus.json`
- **Entity Loot Tables**: 17 files
  - `entities/cave_creeper.json`
  - `entities/ocean_creeper.json`
  - `entities/birch_creeper.json`
  - `entities/dripstone_creeper.json`
  - `entities/dark_oak_creeper.json`
  - `entities/badlands_creeper.json`
  - `entities/beach_creeper.json`
  - `entities/savannah_creeper.json`
  - `entities/desert_creeper.json`
  - `entities/mushroom_creeper.json`
  - `entities/swamp_creeper.json`
  - `entities/jungle_creeper.json`
  - `entities/hills_creeper.json`
  - `entities/spruce_creeper.json`
  - `entities/snowy_creeper.json`
  - `entities/bamboo_creeper.json`
  - And more...

### Existing DataGen Infrastructure
- **DataGenerator Entrypoint**: `CreepersDataGenerator.java` (exists but empty)
- **Block Loot Provider**: `CreepersBlockLootTableProvider.java` (exists but empty)
- **Entity Loot Provider**: `CreepersLootTableProvider.java` (exists but empty)

## Migration Plan

### Phase 1: Block Loot Tables

**File**: `CreepersBlockLootTableProvider.java`

1. **Implement Block Loot Provider**
   - Register provider in `CreepersDataGenerator.java`
   - Use `FabricBlockLootTableProvider` base class
   - Implement `generate()` method

2. **Migrate Block Loot Tables**
   
   **tiny_cactus.json**:
   - Type: Block loot with `survives_explosion` condition
   - Drops: Self (tiny_cactus item)
   - Java equivalent: `dropSelf(ModBlocks.TINY_CACTUS.get())`
   
   **potted_tiny_cactus.json**:
   - Type: Block loot with `survives_explosion` condition
   - Drops: Tiny cactus item (not the potted version)
   - Java equivalent: `add(ModBlocks.POTTED_TINY_CACTUS.get(), createPotFlowerItemTable(ModBlocks.TINY_CACTUS.get()))`

3. **Remove JSON Files**
   - Delete `src/main/resources/data/creeperoverhaul/loot_table/blocks/*.json`

### Phase 2: Entity Loot Tables

**File**: `CreepersLootTableProvider.java`

1. **Update Entity Loot Provider**
   - Register provider in `CreepersDataGenerator.java`
   - Use `SimpleFabricLootTableProvider` with `LootContextParamSets.ENTITY`
   - Implement `generate()` method with all entity loot tables

2. **Common Patterns in Entity Loot Tables**

   All creepers have:
   - **Music Disc Pool**: Tag entry for `minecraft:creeper_drop_music_discs` with skeleton killer condition
   - **Gunpowder Pool**: 0-2 gunpowder with looting enchantment bonus (0-1 extra per level)

3. **Entity-Specific Loot Patterns**

   | Entity | Additional Drops |
   |--------|-----------------|
   | `cave_creeper` | 0-2 stone, rare ores (iron, gold, emerald, diamond with player kill + chance) |
   | `ocean_creeper` | 0-2 cod, 0-2 salmon |
   | `beach_creeper` | 0-2 sand, 0-2 prismarine_shard, 0-2 seagrass, rare nautilus_shell |
   | `badlands_creeper` | 0-6 gold_nugget |
   | `bamboo_creeper` | 0-2 bamboo |
   | `dripstone_creeper` | 0-2 pointed_dripstone |
   | `snowy_creeper` | 0-2 white_wool |
   | `hills_creeper` | (check JSON - likely stone/dirt) |
   | `jungle_creeper` | (check JSON - likely vines/cocoa) |
   | `mushroom_creeper` | (check JSON - likely mushrooms) |
   | `swamp_creeper` | (check JSON - likely lily pads/clay) |
   | `savannah_creeper` | (check JSON) |
   | `desert_creeper` | (check JSON - likely sand) |
   | `dark_oak_creeper` | (check JSON) |
   | `spruce_creeper` | (check JSON - likely spruce items) |
   | `birch_creeper` | (check JSON - likely birch items) |

4. **Key Loot Functions to Use**

   ```java
   // Basic item drop with count range
   LootItem.lootTableItem(Items.GUNPOWDER)
       .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
       .apply(EnchantedCountIncreaseFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
   
   // Rare drop with player kill condition
   LootItem.lootTableItem(Items.DIAMOND)
       .when(LootItemKilledByPlayerCondition.killedByPlayer())
       .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registryLookup, 0.01f, 0.03f))
   
   // Music disc tag with skeleton killer condition
   TagEntry.expandTags(ItemTags.CREEPER_DROP_MUSIC_DISCS)
       .when(LootItemEntityPropertyCondition.hasProperties(
           LootContext.EntityTarget.ATTACKER,
           EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.SKELETON))
       ))
   ```

5. **Remove JSON Files**
   - Delete `src/main/resources/data/creeperoverhaul/loot_table/entities/*.json`

### Phase 3: Register Providers

**File**: `CreepersDataGenerator.java`

```java
@Override
public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    
    // Block loot tables
    pack.addProvider(CreepersBlockLootTableProvider::new);
    
    // Entity loot tables
    pack.addProvider((FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) -> 
        new CreepersLootTableProvider(output, registries, LootContextParamSets.ENTITY));
}
```

### Phase 4: Verification

1. **Run DataGen**: `./gradlew runDatagen`
2. **Verify Output**: Check `build/datagen/` for generated loot tables
3. **Compare**: Ensure generated JSON matches original JSON structure
4. **Test**: Run game and verify drops work correctly

## Implementation Order

1. **Complete Block Loot Provider** (simpler, only 2 blocks)
2. **Complete Entity Loot Provider** (17 entities)
3. **Register all providers** in DataGenerator entrypoint
4. **Delete JSON files** after verification
5. **Run and test** data generation

## Code Structure Example

### Block Loot Provider
```java
public class CreepersBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected CreepersBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.TINY_CACTUS.get());
        add(ModBlocks.POTTED_TINY_CACTUS.get(), createPotFlowerItemTable(ModBlocks.TINY_CACTUS.get()));
    }
}
```

### Entity Loot Provider (Example for Cave Creeper)
```java
public class CreepersLootTableProvider extends SimpleFabricLootTableProvider {
    public CreepersLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, LootContextParamSet lootContextType) {
        super(output, registryLookup, lootContextType);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter) {
        // Cave Creeper
        exporter.accept(
            ModEntities.CAVE_CREEPER.get().getDefaultLootTable(),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.GUNPOWDER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(UniformGenerator.between(0, 1)))))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.STONE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(UniformGenerator.between(0, 1)))))
                // ... more pools for rare ores and music discs
        );
        
        // Repeat for all 17 entities...
    }
}
```

## Notes

- The project uses `ResourcefulRegistries` for registration - loot table keys will need to match entity registry names
- Entity loot tables use `LootContextParamSets.ENTITY`
- Block loot tables use `LootContextParamSets.BLOCK` (handled automatically by `FabricBlockLootTableProvider`)
- Some entities have complex loot tables with multiple conditional drops - these need careful translation
- Make sure to import all necessary classes from `net.minecraft.world.level.storage.loot.*`

## Files to Modify

1. `src/main/java/tech/thatgravyboat/creeperoverhaul/datagen/CreepersDataGenerator.java`
2. `src/main/java/tech/thatgravyboat/creeperoverhaul/datagen/CreepersBlockLootTableProvider.java`
3. `src/main/java/tech/thatgravyboat/creeperoverhaul/datagen/CreepersLootTableProvider.java`

## Files to Delete (After Verification)

1. `src/main/resources/data/creeperoverhaul/loot_table/blocks/tiny_cactus.json`
2. `src/main/resources/data/creeperoverhaul/loot_table/blocks/potted_tiny_cactus.json`
3. All files in `src/main/resources/data/creeperoverhaul/loot_table/entities/`

## Testing Checklist

- [ ] Run `./gradlew runDatagen` successfully
- [ ] Verify `build/datagen/data/creeperoverhaul/loot_table/` contains generated files
- [ ] Compare generated JSON with original JSON
- [ ] Run Minecraft and test:
  - [ ] Breaking tiny_cactus drops item
  - [ ] Breaking potted_tiny_cactus drops tiny_cactus
  - [ ] Killing creepers drops appropriate loot
  - [ ] Music discs drop when killed by skeleton
  - [ ] Rare drops work with looting enchantment
