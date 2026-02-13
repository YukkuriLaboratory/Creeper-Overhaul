package tech.thatgravyboat.creeperoverhaul.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CreepersDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        // Block loot tables
        pack.addProvider(CreepersBlockLootTableProvider::new);
        
        // Entity loot tables
        pack.addProvider(CreepersLootTableProvider::new);
    }
}
