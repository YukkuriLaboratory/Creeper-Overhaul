package tech.thatgravyboat.creeperoverhaul.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class CreepersDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        // Block loot tables
        pack.addProvider(CreepersBlockLootTableProvider::new);
        
        // Entity loot tables
        pack.addProvider((FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) -> 
            new CreepersLootTableProvider(output, registries, LootContextParamSets.ENTITY));
    }
}
