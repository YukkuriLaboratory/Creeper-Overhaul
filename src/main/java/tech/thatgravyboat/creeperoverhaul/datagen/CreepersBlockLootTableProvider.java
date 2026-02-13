package tech.thatgravyboat.creeperoverhaul.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;

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
