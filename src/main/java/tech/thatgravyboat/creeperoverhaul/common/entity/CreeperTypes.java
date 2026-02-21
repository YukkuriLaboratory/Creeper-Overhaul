package tech.thatgravyboat.creeperoverhaul.common.entity;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import ladysnake.blast.common.init.BlastItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.config.SpawningConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.custom.PufferfishCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;

public class CreeperTypes {

    public static final CreeperType JUNGLE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/jungle/jungle_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/jungle/jungle_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("jungle"))
            .setAnimation(Creepers.id("jungle"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowJungleCreeperSpawning)
            .setLootKey(createEntityLootKey("jungle_creeper"))
            .setBombSupplier(() -> BlastItems.CONFETTI_BOMB)
            .build();

    public static final CreeperType BAMBOO = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/bamboo/bamboo_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/bamboo/bamboo_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("bamboo"))
            .setAnimation(Creepers.id("bamboo"))
            .setMelee(9)
            .addAfraidOf(EntityType.PANDA)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 20);
                builder.add(Attributes.ATTACK_DAMAGE, 2);
                builder.add(Attributes.ENTITY_INTERACTION_RANGE, 2);
            })
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHitSounds(ModSounds.PLANT_HIT)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBambooCreeperSpawning)
            .setLootKey(createEntityLootKey("bamboo_creeper"))
            .build();

    public static final CreeperType DESERT = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/desert/desert_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/desert/desert_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("desert"))
            .setShearedModel(Creepers.id("desert_sheared"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS))
            .setAnimation(Creepers.id("desert"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSources)
            .addAttributes(builder -> builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5))
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDesertCreeperSpawning)
            .setLootKey(createEntityLootKey("desert_creeper"))
            .setBombSupplier(() -> BlastItems.SAND_BOMB)
            .build();

    public static final CreeperType BADLANDS = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/badlands/badlands_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/badlands/badlands_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("badlands"))
            .setShearedModel(Creepers.id("badlands_sheared"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS))
            .setAnimation(Creepers.id("badlands"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.CACTUS)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 30);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
            })
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBadlandsCreeperSpawning)
            .setLootKey(createEntityLootKey("badlands_creeper"))
            .setBombSupplier(() -> BlastItems.GOLDEN_BOMB)
            .build();

    public static final CreeperType HILLS = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/hills/hills_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/hills/hills_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("hills"))
            .setAnimation(Creepers.id("hills"))
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 30);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
            })
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowHillsCreeperSpawning)
            .setLootKey(createEntityLootKey("hills_creeper"))
            .build();

    public static final CreeperType SAVANNAH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/savannah/savannah_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/savannah/savannah_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("savannah"))
            .setAnimation(Creepers.id("savannah"))
            .setMelee(5)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 25);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
                builder.add(Attributes.ATTACK_DAMAGE, 3);
            })
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setHitSounds(ModSounds.WOOD_HIT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSavannahCreeperSpawning)
            .setLootKey(createEntityLootKey("savannah_creeper"))
            .build();

    public static final CreeperType MUSHROOM = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/mushroom/mushroom_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/mushroom/mushroom_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_4.png"))
            .setModel(Creepers.id("mushroom"))
            .setAnimation(Creepers.id("mushroom"))
            .addPotionsWhenDying(new MobEffectInstance(MobEffects.POISON, 100, 1))
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.MYCELIUM.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowMushroomCreeperSpawning)
            .setLootKey(createEntityLootKey("mushroom_creeper"))
            .setBombSupplier(() -> BlastItems.DIAMOND_BOMB)
            .build();

    public static final CreeperType SWAMP = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/swamp/swamp_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/swamp/swamp_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("swamp"))
            .setAnimation(Creepers.id("swamp"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add("swim_speed", 2))
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSwampCreeperSpawning)
            .setLootKey(createEntityLootKey("swamp_creeper"))
            .setBombSupplier(() -> BlastItems.SLIME_BOMB)
            .build();

    public static final CreeperType DRIPSTONE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/dripstone/dripstone_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/dripstone/dripstone_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("dripstone"))
            .setAnimation(Creepers.id("dripstone"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add(Attributes.MAX_HEALTH, 12))
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDripstoneCreeperSpawning)
            .setLootKey(createEntityLootKey("dripstone_creeper"))
            .setBombSupplier(() -> BlastItems.PEARL_BOMB)
            .build();

    public static final CreeperType CAVE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/cave/cave_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/cave/cave_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("cave"))
            .setAnimation(Creepers.id("cave"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 25);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
            })
            .addReplacer(state -> state.is(Blocks.STONE), random -> random.nextInt(100) == 0 ? Blocks.COAL_ORE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowCaveCreeperSpawning)
            .setLootKey(createEntityLootKey("cave_creeper"))
            .setBombSupplier(() -> BlastItems.AMETHYST_BOMB)
            .build();

    public static final CreeperType DARK_OAK = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/dark_oak/dark_oak_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/dark_oak/dark_oak_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("dark_oak"))
            .setAnimation(Creepers.id("dark_oak"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addInflictingPotion(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0))
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDarkOakCreeperSpawning)
            .setLootKey(createEntityLootKey("dark_oak_creeper"))
            .setBombSupplier(() -> BlastItems.DIRT_BOMB)
            .build();

    public static final CreeperType SPRUCE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/spruce/spruce_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/spruce/spruce_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("spruce"))
            .setAnimation(Creepers.id("spruce"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.PODZOL.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSpruceCreeperSpawning)
            .setLootKey(createEntityLootKey("spruce_creeper"))
            .build();

    public static final CreeperType BEACH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/beach/beach_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/beach/beach_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("beach"))
            .setAnimation(Creepers.id("beach"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.DROWN)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 15);
                builder.add("swim_speed", 2);
            })
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBeachCreeperSpawning)
            .setLootKey(createEntityLootKey("beach_creeper"))
            .build();

    public static final CreeperType SNOWY = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/snowy/snowy_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/snowy/snowy_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("snowy"))
            .setAnimation(Creepers.id("snowy"))
            .setMelee(5)
            .addAttributes(builder -> builder.add(Attributes.ATTACK_DAMAGE, 4))
            .addAttackingEntities(Stray.class)
            .setCanSpawn(() -> SpawningConfig.allowSnowyCreeperSpawning)
            .setLootKey(createEntityLootKey("snowy_creeper"))
            .setBombSupplier(() -> BlastItems.FROST_BOMB)
            .build();

    public static final CreeperType OCEAN = new CreeperType.Builder()
            .setTexture(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    PufferfishCreeper.Variant variant = pufferfish.getVariant();
                    return Creepers.id("textures/entity/ocean/" + variant.name().toLowerCase(Locale.ROOT) + "_" + id + ".png");
                }
                return Creepers.id("textures/entity/ocean/brown_1.png");
            })
            .setGlowingTexture(Creepers.id("textures/entity/ocean/glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_4.png"))
            .setModel(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    return Creepers.id("ocean_" + id);
                }
                return Creepers.id("ocean_1");
            })
            .setAnimation(Creepers.id("ocean"))
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 16);
                builder.add(Attributes.MOVEMENT_SPEED, 1.7);
                builder.add(Attributes.ENTITY_INTERACTION_RANGE, 2.5);
                builder.add("swim_speed", 1.7);
            })
            .setDeathSounds(ModSounds.OCEAN_DEATH)
            .setHurtSound(creeper -> {
                if (creeper instanceof PufferfishCreeper fish && fish.getPuffId() == 3) {
                    return ModSounds.OCEAN_HURT_INFLATED.get();
                }
                return ModSounds.OCEAN_HURT_DEFLATED.get();
            })
            .setFlopSounds(() -> SoundEvents.PUFFER_FISH_FLOP)
            .setCanSpawn(() -> SpawningConfig.allowOceanCreeperSpawning)
            .setLootKey(createEntityLootKey("ocean_creeper"))
            .setBombSupplier(() -> BlastItems.NAVAL_MINE)
            .build();

    public static final CreeperType BIRCH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/birch/birch_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/birch/birch_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("birch"))
            .setAnimation(Creepers.id("birch"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add(Attributes.MAX_HEALTH, 20))
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBirchCreeperSpawning)
            .setLootKey(createEntityLootKey("birch_creeper"))
            .build();

    public static final Set<CreeperType> entries = new HashSet<>();
    static {
        entries.add(JUNGLE);
        entries.add(BAMBOO);
        entries.add(DESERT);
        entries.add(BADLANDS);
        entries.add(HILLS);
        entries.add(SAVANNAH);
        entries.add(MUSHROOM);
        entries.add(SWAMP);
        entries.add(DRIPSTONE);
        entries.add(CAVE);
        entries.add(DARK_OAK);
        entries.add(SPRUCE);
        entries.add(BEACH);
        entries.add(SNOWY);
        entries.add(OCEAN);
        entries.add(BIRCH);
    }

    public static ResourceKey<@NotNull LootTable> createEntityLootKey(String entityName) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(Creepers.MODID, "entities/" + entityName));
    }
}
