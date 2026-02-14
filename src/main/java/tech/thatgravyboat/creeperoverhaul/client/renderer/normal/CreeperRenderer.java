package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<@NotNull E, @NotNull CreeperRenderState> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        withRenderLayer(new CreeperGlowLayer<>(this));
        withRenderLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public void defaultRender(PoseStack poseStack, E creeper, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (creeper.isInvisible()) return;

        float f = creeper.getSwelling(partialTick);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
        super.defaultRender(poseStack, creeper, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull E entity) {
        return this.model.getTextureResource(entity);
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, E animatable, RenderType renderType, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int color) {
        float f = animatable.getSwelling(partialTick);
        f = (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
        super.reRender(
                model, poseStack, bufferSource, animatable, renderType, buffer, partialTick,
                packedLight, getOverlayCoords(animatable, f), color
        );
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
    }

    @Override
    public @org.jspecify.annotations.Nullable RenderType getRenderType(R renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}
