package tech.thatgravyboat.creeperoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class ModItems {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Creepers.MODID);

    public static final Supplier<SpawnEggItem> JUNGLE_SPAWN_EGG = ITEMS.register("jungle_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.JUNGLE_CREEPER, 0x507541, 0x59461A, new Item.Properties()));

    public static final Supplier<SpawnEggItem> BAMBOO_SPAWN_EGG = ITEMS.register("bamboo_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.BAMBOO_CREEPER, 0x599003, 0x1F3322, new Item.Properties()));

    public static final Supplier<SpawnEggItem> DESERT_SPAWN_EGG = ITEMS.register("desert_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.DESERT_CREEPER, 0xD1BA8A, 0x966E3D, new Item.Properties()));

    public static final Supplier<SpawnEggItem> BADLANDS_SPAWN_EGG = ITEMS.register("badlands_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.BADLANDS_CREEPER, 0xA36C4B, 0xE8C943, new Item.Properties()));

    public static final Supplier<SpawnEggItem> HILLS_SPAWN_EGG = ITEMS.register("hills_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.HILLS_CREEPER, 0x9A9B88, 0x4F5E53, new Item.Properties()));

    public static final Supplier<SpawnEggItem> SAVANNAH_SPAWN_EGG = ITEMS.register("savannah_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.SAVANNAH_CREEPER, 0xAD5D32, 0x6E3A29, new Item.Properties()));

    public static final Supplier<SpawnEggItem> MUSHROOM_SPAWN_EGG = ITEMS.register("mushroom_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.MUSHROOM_CREEPER, 0x60526A, 0xD15A4B, new Item.Properties()));

    public static final Supplier<SpawnEggItem> SWAMP_SPAWN_EGG = ITEMS.register("swamp_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.SWAMP_CREEPER, 0x4F5835, 0x1B2824, new Item.Properties()));

    public static final Supplier<SpawnEggItem> DRIPSTONE_SPAWN_EGG = ITEMS.register("dripstone_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.DRIPSTONE_CREEPER, 0x836356, 0xA08D71, new Item.Properties()));

    public static final Supplier<SpawnEggItem> CAVE_SPAWN_EGG = ITEMS.register("cave_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.CAVE_CREEPER, 0x7E847C, 0x3A3F3E, new Item.Properties()));

    public static final Supplier<SpawnEggItem> DARK_OAK_SPAWN_EGG = ITEMS.register("dark_oak_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.DARK_OAK_CREEPER, 0x3F311D, 0x1E1519, new Item.Properties()));

    public static final Supplier<SpawnEggItem> SPRUCE_SPAWN_EGG = ITEMS.register("spruce_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.SPRUCE_CREEPER, 0x888788, 0x738552, new Item.Properties()));

    public static final Supplier<SpawnEggItem> BEACH_SPAWN_EGG = ITEMS.register("beach_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.BEACH_CREEPER, 0xDAC896, 0x704E3A, new Item.Properties()));

    public static final Supplier<SpawnEggItem> SNOWY_SPAWN_EGG = ITEMS.register("snowy_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.SNOWY_CREEPER, 0xBECDD8, 0xE8F8F9, new Item.Properties()));

    public static final Supplier<SpawnEggItem> OCEAN_SPAWN_EGG = ITEMS.register("ocean_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.OCEAN_CREEPER, 0x84D89E, 0xEFA662, new Item.Properties()));

    public static final Supplier<SpawnEggItem> BIRCH_SPAWN_EGG = ITEMS.register("birch_creeper_spawn_egg", () -> createSpawnEgg(
            ModEntities.BIRCH_CREEPER, 0xE1D6C9, 0x36342A, new Item.Properties()));

    public static final Supplier<Item> TINY_CACTUS =  ITEMS.register("tiny_cactus", () -> new BlockItem(ModBlocks.TINY_CACTUS.get(), new Item.Properties().component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).build())));

    // Todo: move primary Color and secondary Color to datagen
    public static <E extends Mob, T extends EntityType<@NotNull E>> SpawnEggItem createSpawnEgg(Supplier<T> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        return new SpawnEggItem(properties.spawnEgg(entity.get()));
    }
}
