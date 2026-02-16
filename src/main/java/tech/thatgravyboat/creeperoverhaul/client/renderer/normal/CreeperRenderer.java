package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<@NotNull E, @NotNull CreeperRenderState> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        withRenderLayer(new CreeperGlowLayer<>(this));
        withRenderLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(@NotNull CreeperRenderState renderState) {
        return this.model.getTextureResource(renderState);
    }

    @Override
    public CreeperRenderState createRenderState(E animatable, @org.jspecify.annotations.Nullable Void relatedObject) {
        return new CreeperRenderState();
    }

    @Override
    public void extractRenderState(E entity, CreeperRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        renderState.swelling = entity.getSwelling(partialTick);
        renderState.isPowered = entity.isPowered();
        renderState.type = entity.type;
        renderState.isSheared = entity.isSheared();
        renderState.baseCreeper = entity;
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<@NotNull CreeperRenderState> renderPassInfo, float widthScale, float heightScale) {
        super.scaleModelForRender(renderPassInfo, widthScale, heightScale);

        float swellFactor = renderPassInfo.renderState().swelling;
        float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = (float) Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
        float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;

        renderPassInfo.poseStack().scale(horizontalSwell, verticalSwell, horizontalSwell);
    }

    @Override
    public int getPackedOverlay(E animatable, @org.jspecify.annotations.Nullable Void relatedObject, float u, float partialTick) {
        float swell = animatable.getSwelling(partialTick);
        float overlay = (int) (swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
        return super.getPackedOverlay(animatable, relatedObject, overlay, partialTick);
    }

    @Override
    public @org.jspecify.annotations.Nullable RenderType getRenderType(CreeperRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}
