package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.CreeperRenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperPowerLayer<E extends BaseCreeper> extends GeoRenderLayer<E, Void, CreeperRenderState> {

    public CreeperPowerLayer(GeoRenderer<E, Void, CreeperRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<CreeperRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        CreeperRenderState creeper = renderPassInfo.renderState();

        if (!creeper.isPowered || PlatformUtils.shouldHidePowerLayer()) return;
        if (creeper.type.chargedTexture() == null) return;

        Identifier texture = creeper.type.chargedTexture().apply(creeper.baseCreeper);
        if (texture == null) return;

        float partialTick = renderPassInfo.renderState()
                .getOrDefaultGeckolibData(DataTickets.PARTIAL_TICK, 0f);
        float f = (float) creeper.baseCreeper.tickCount + partialTick;

        RenderType renderType = CreeperRenderTypes.getSwirl(
                texture,
                f * 0.005F % 1F,
                f * 0.005F % 1F
        );

        getRenderer().submitRenderTasks(renderPassInfo, renderTasks.order(1), renderType);
    }
}
