package tech.thatgravyboat.creeperoverhaul.client;

import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class CreeperRenderTypes {

    public static net.minecraft.client.renderer.rendertype.RenderType getSwirl(Identifier location, float u, float v) {
        return RenderTypes.entityTranslucent(location);
    }

    public static net.minecraft.client.renderer.rendertype.RenderType getTransparentEyes(Identifier location) {
        return RenderTypes.entityTranslucent(location);
    }
}
