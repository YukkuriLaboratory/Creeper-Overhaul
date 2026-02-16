package tech.thatgravyboat.creeperoverhaul.client;

import java.util.function.Function;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperModel;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.client.renderer.replaced.ReplacedCreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.common.config.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;

public class CreepersClient {

    public static void init() {
        Creepers.CONFIGURATOR.register(ClientConfig.class);
    }

    @SuppressWarnings("unchecked")
    public static void registerEntityLayers(Function<PlayerModelType, AvatarRenderer> getter) {
//        AvatarRenderer<@NotNull AbstractClientPlayer> defaultRenderer = getter.apply(PlayerModelType.WIDE);
//        AvatarRenderer<@NotNull AbstractClientPlayer> slimRenderer = getter.apply(PlayerModelType.SLIM);
//        LivingEntityRendererInvoker<AvatarRenderState, PlayerModel> defaultInvoker = ((LivingEntityRendererInvoker<AvatarRenderState, PlayerModel>) defaultRenderer);
//        LivingEntityRendererInvoker<AvatarRenderState, PlayerModel> slimInvoker = ((LivingEntityRendererInvoker<AvatarRenderState, PlayerModel>) slimRenderer);
//        if (defaultRenderer != null && slimRenderer != null) {
//            defaultInvoker.invokeAddLayer(new CosmeticLayer(defaultRenderer));
//            slimInvoker.invokeAddLayer(new CosmeticLayer(slimRenderer));
//        }
    }

    public static void registerRenderers() {
        registerRenderer(ModEntities.JUNGLE_CREEPER, createRenderer(CreeperTypes.JUNGLE));
        registerRenderer(ModEntities.BAMBOO_CREEPER, createRenderer(CreeperTypes.BAMBOO));
        registerRenderer(ModEntities.DESERT_CREEPER, createRenderer(CreeperTypes.DESERT));
        registerRenderer(ModEntities.BADLANDS_CREEPER, createRenderer(CreeperTypes.BADLANDS));
        registerRenderer(ModEntities.HILLS_CREEPER, createRenderer(CreeperTypes.HILLS));
        registerRenderer(ModEntities.SAVANNAH_CREEPER, createRenderer(CreeperTypes.SAVANNAH));
        registerRenderer(ModEntities.MUSHROOM_CREEPER, createRenderer(CreeperTypes.MUSHROOM));
        registerRenderer(ModEntities.SWAMP_CREEPER, createRenderer(CreeperTypes.SWAMP));
        registerRenderer(ModEntities.DRIPSTONE_CREEPER, createRenderer(CreeperTypes.DRIPSTONE));
        registerRenderer(ModEntities.CAVE_CREEPER, createRenderer(CreeperTypes.CAVE));
        registerRenderer(ModEntities.DARK_OAK_CREEPER, createRenderer(CreeperTypes.DARK_OAK));
        registerRenderer(ModEntities.SPRUCE_CREEPER, createRenderer(CreeperTypes.SPRUCE));
        registerRenderer(ModEntities.BEACH_CREEPER, createRenderer(CreeperTypes.BEACH));
        registerRenderer(ModEntities.SNOWY_CREEPER, createRenderer(CreeperTypes.SNOWY));
        registerRenderer(ModEntities.OCEAN_CREEPER, createRenderer(CreeperTypes.OCEAN));
        registerRenderer(ModEntities.BIRCH_CREEPER, createRenderer(CreeperTypes.BIRCH));
        if (ClientConfig.replaceDefaultCreeper) {
            registerRenderer(EntityType.CREEPER, ReplacedCreeperRenderer::new);
        }
        registerBlockRenderType(ModBlocks.TINY_CACTUS, ChunkSectionLayer.CUTOUT);
        registerBlockRenderType(ModBlocks.POTTED_TINY_CACTUS, ChunkSectionLayer.CUTOUT);
    }

    private static <E extends BaseCreeper> EntityRendererProvider<E> createRenderer(CreeperType type) {
        return manager -> new CreeperRenderer<>(manager, new CreeperModel<>(type));
    }

    public static <E extends Entity> void registerRenderer(Supplier<EntityType<E>> entity, EntityRendererProvider<E> renderer) {
        registerRenderer(entity.get(), renderer);
    }

    public static <E extends Entity> void registerRenderer(EntityType<E> entity, EntityRendererProvider<E> renderer) {
        EntityRendererRegistry.register(entity, renderer);
    }

    public static void registerBlockRenderType(Supplier<Block> block, ChunkSectionLayer type) {
        BlockRenderLayerMap.putBlock(block.get(), type);
    }
}
