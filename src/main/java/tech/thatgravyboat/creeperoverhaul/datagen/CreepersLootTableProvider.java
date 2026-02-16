package tech.thatgravyboat.creeperoverhaul.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class CreepersLootTableProvider extends SimpleFabricLootTableProvider {

    private final CompletableFuture<HolderLookup.Provider> lookup;
    private HolderLookup.Provider registries;

    public CreepersLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
        this.lookup = registryLookup;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter) {
        // Join the registry lookup future to get the actual provider
        this.registries = lookup.join();

        // Cave Creeper - has gunpowder, stone, and rare ores
        exporter.accept(
            createEntityLootKey("cave_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.STONE, 0, 2, 0, 1))
                .withPool(createRareOrePool(Items.RAW_IRON, 0.20f, 0.22f))
                .withPool(createRareOrePool(Items.RAW_GOLD, 0.10f, 0.12f))
                .withPool(createRareOrePool(Items.EMERALD, 0.05f, 0.07f))
                .withPool(createRareOrePool(Items.DIAMOND, 0.01f, 0.03f))
                .withPool(createMusicDiscPool())
        );

        // Ocean Creeper - cod and salmon
        exporter.accept(
            createEntityLootKey("ocean_creeper"),
            LootTable.lootTable()
                .withPool(createBasicPool(Items.COD, 0, 2, 0, 2))
                .withPool(createBasicPool(Items.SALMON, 0, 2, 0, 2))
                .withPool(createMusicDiscPool())
        );

        // Birch Creeper - gunpowder and birch log
        exporter.accept(
            createEntityLootKey("birch_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.BIRCH_LOG, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Dripstone Creeper - gunpowder and pointed dripstone
        exporter.accept(
            createEntityLootKey("dripstone_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.POINTED_DRIPSTONE, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Dark Oak Creeper - gunpowder, dark oak log, and cobweb
        exporter.accept(
            createEntityLootKey("dark_oak_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.DARK_OAK_LOG, 0, 2, 0, 1))
                .withPool(createBasicPool(Items.COBWEB, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Badlands Creeper - gunpowder and gold nuggets
        exporter.accept(
            createEntityLootKey("badlands_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 6)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1)))))
                .withPool(createMusicDiscPool())
        );

        // Beach Creeper - gunpowder, sand, prismarine shard, seagrass, and rare nautilus shell
        exporter.accept(
            createEntityLootKey("beach_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.SAND, 0, 2, 0, 1))
                .withPool(createBasicPool(Items.PRISMARINE_SHARD, 0, 2, 0, 1))
                .withPool(createBasicPool(Items.SEAGRASS, 0, 2, 0, 1))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                        registries, 0.005f, 0.025f)))
                .withPool(createMusicDiscPool())
        );

        // Savannah Creeper - acacia log only (no gunpowder)
        exporter.accept(
            createEntityLootKey("savannah_creeper"),
            LootTable.lootTable()
                .withPool(createBasicPool(Items.ACACIA_LOG, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Desert Creeper - gunpowder and cactus
        exporter.accept(
            createEntityLootKey("desert_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.CACTUS, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Mushroom Creeper - gunpowder only
        exporter.accept(
            createEntityLootKey("mushroom_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createMusicDiscPool())
        );

        // Swamp Creeper - gunpowder and bone
        exporter.accept(
            createEntityLootKey("swamp_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.BONE, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Jungle Creeper - gunpowder only
        exporter.accept(
            createEntityLootKey("jungle_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createMusicDiscPool())
        );

        // Hills Creeper - gunpowder only
        exporter.accept(
            createEntityLootKey("hills_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createMusicDiscPool())
        );

        // Spruce Creeper - gunpowder and spruce log
        exporter.accept(
            createEntityLootKey("spruce_creeper"),
            LootTable.lootTable()
                .withPool(createGunpowderPool())
                .withPool(createBasicPool(Items.SPRUCE_LOG, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );

        // Snowy Creeper - white wool only
        exporter.accept(
            createEntityLootKey("snowy_creeper"),
            LootTable.lootTable()
                .withPool(createBasicPool(Items.WHITE_WOOL, 0, 2, 0, 2))
        );

        // Bamboo Creeper - bamboo only
        exporter.accept(
            createEntityLootKey("bamboo_creeper"),
            LootTable.lootTable()
                .withPool(createBasicPool(Items.BAMBOO, 0, 2, 0, 1))
                .withPool(createMusicDiscPool())
        );
    }

    private ResourceKey<LootTable> createEntityLootKey(String entityName) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(Creepers.MODID, "entities/" + entityName));
    }

    private LootPool.Builder createGunpowderPool() {
        return LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(Items.GUNPOWDER)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1))));
    }

    private LootPool.Builder createBasicPool(net.minecraft.world.item.Item item, float min, float max, float lootingMin, float lootingMax) {
        return LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(lootingMin, lootingMax))));
    }

    private LootPool.Builder createRareOrePool(net.minecraft.world.item.Item item, float baseChance, float enchantedBase) {
        return LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(item))
            .when(LootItemKilledByPlayerCondition.killedByPlayer())
            .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                registries, baseChance, enchantedBase));
    }

    private LootPool.Builder createMusicDiscPool() {
        return LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1))
            .add(TagEntry.tagContents(ItemTags.CREEPER_DROP_MUSIC_DISCS));
    }
}
