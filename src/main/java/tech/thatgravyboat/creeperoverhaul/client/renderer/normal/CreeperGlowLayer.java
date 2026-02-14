package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperGlowLayer<E extends BaseCreeper> extends GeoRenderLayer<E, Void, CreeperRenderState> {

    public CreeperGlowLayer(CreeperRenderer<E> renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<CreeperRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        super.submitRenderTask(renderPassInfo, renderTasks);
    }

    @Override
    public void render(PoseStack poseStack, CreeperRenderState creeper, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        float f = creeper.swelling;
        if (f > 0f || creeper.isPowered) {
            if (creeper.isPowered) f = 1f;

            CreeperType type = creeper.type;

            VertexConsumer glowConsumer = bufferSource.getBuffer(RenderTypes.getTransparentEyes(type.glowingTexture().apply(creeper)));

            getRenderer().(
                    getDefaultBakedModel(creeper), poseStack, bufferSource, creeper,
                    RenderTypes.getTransparentEyes(type.glowingTexture().apply(creeper)), glowConsumer,
                    partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFF | Mth.ceil(f * 240) << 24
            );
        }
    }
}
