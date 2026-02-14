package tech.thatgravyboat.creeperoverhaul;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;

public class CreepersClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CreepersClient.init();
        CreepersClient.registerRenderers();

//        CoreShaderRegistrationCallback.EVENT.register((registrationCallback) ->
//            RenderTypes.registerShaders((id, format, callback) -> {
//                try {
//                    registrationCallback.register(id, format, callback);
//                } catch (Exception e) {
//                    throw new RuntimeException("[Creeper Overhaul] Shaders could not be reloaded", e);
//                }
//            })
//        );
    }
}
