package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

public class ReplacedCreeperPowerLayer<R extends CreeperRenderState & GeoRenderState> extends GeoRenderLayer<ReplacedCreeper, Creeper, R> {

    public ReplacedCreeperPowerLayer(ReplacedCreeperRenderer renderer) {
        super(renderer);
    }

    @SuppressWarnings("unused")
    @Override
    public void render(PoseStack poseStack, ReplacedCreeper animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
    }
}
