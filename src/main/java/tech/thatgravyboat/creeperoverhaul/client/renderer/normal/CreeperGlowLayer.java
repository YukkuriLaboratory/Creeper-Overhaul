package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.CreeperRenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperGlowLayer<E extends BaseCreeper> extends GeoRenderLayer<E, Void, CreeperRenderState> {

    public CreeperGlowLayer(GeoRenderer<E, Void, CreeperRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<CreeperRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        CreeperRenderState creeper = renderPassInfo.renderState();
        float f = creeper.swelling;

        if (f <= 0f && !creeper.isPowered) return;

        if (creeper.isPowered) f = 1f;

        CreeperType type = creeper.type;
        if (type.glowingTexture() == null) return;

        Identifier texture = type.glowingTexture().apply(creeper.baseCreeper);
        if (texture == null) return;

        RenderType renderType = CreeperRenderTypes.getTransparentEyes(texture);

        Integer previousColor = creeper.getGeckolibData(DataTickets.RENDER_COLOR);
        int alpha = Mth.clamp(Mth.ceil(f * 240), 0, 255);
        int color = 0xFFFFFF | alpha << 24;
        creeper.addGeckolibData(DataTickets.RENDER_COLOR, color);

        getRenderer().submitRenderTasks(renderPassInfo, renderTasks.order(1), renderType);

        creeper.addGeckolibData(DataTickets.RENDER_COLOR, previousColor != null ? previousColor : 0xFFFFFFFF);
    }
}
