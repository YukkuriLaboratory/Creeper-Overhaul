package tech.thatgravyboat.creeperoverhaul;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;

public class CreepersClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CreepersClient.init();
        CreepersClient.registerRenderers();
    }
}
