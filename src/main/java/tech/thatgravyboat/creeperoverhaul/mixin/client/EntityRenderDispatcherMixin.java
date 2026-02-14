package tech.thatgravyboat.creeperoverhaul.mixin.client;

import java.util.Map;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.PlayerModelType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {


    @Shadow
    private Map<PlayerModelType, AvatarRenderer<@NotNull AbstractClientPlayer>> playerRenderers;

    @Inject(
            method = "onResourceManagerReload",
            at = @At("TAIL")
    )
    private void rbees$onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
//        CreepersClient.registerEntityLayers(
//                id -> playerRenderers.get(id)
//        );
    }
}