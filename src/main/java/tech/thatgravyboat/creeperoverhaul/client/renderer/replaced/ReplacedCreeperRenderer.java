package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

public class ReplacedCreeperRenderer<R extends CreeperRenderState & GeoRenderState> extends GeoReplacedEntityRenderer<ReplacedCreeper, Creeper, R> {

    public ReplacedCreeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedCreeperModel<>(), new ReplacedCreeper());
        getRenderLayers();
        withRenderLayer(new ReplacedCreeperGlowLayer<>(this));
        withRenderLayer(new ReplacedCreeperPowerLayer<>(this));
    }

    @Override
    public void scaleModelForRender(@NonNull RenderPassInfo<@NonNull R> renderPassInfo, float widthScale, float heightScale) {
        super.scaleModelForRender(renderPassInfo, widthScale, heightScale);

        float swellFactor = renderPassInfo.renderState().swelling;
        float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = (float)Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
        float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;

        renderPassInfo.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);
    }

//    @Override
//    public void render(Creeper entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        if (entity.isInvisible()) return;
//        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
//    }

    @Override
    public int getPackedOverlay(ReplacedCreeper animatable, Creeper replacedEntity, float u, float partialTick) {
        return super.getPackedOverlay(animatable, replacedEntity, getSwellOverlay(replacedEntity, u), partialTick);
    }

    protected float getSwellOverlay(Creeper entity, float u) {
        float swell = entity.getSwelling(u);

        return (int) (swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
    }

    @Override
    public @Nullable RenderType getRenderType(R renderState, @NonNull Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

//    @Override
//    public boolean shouldShowName(@NonNull Creeper entity) {
//        if (this.currentEntity == null) return false;
//        return super.shouldShowName(entity);
//    }
}
