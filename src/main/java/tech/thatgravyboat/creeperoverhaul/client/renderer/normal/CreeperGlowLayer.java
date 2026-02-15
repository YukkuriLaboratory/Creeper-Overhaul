package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperGlowLayer<E extends BaseCreeper> extends TextureLayerGeoLayer<@NotNull E, @NotNull Void, @NotNull CreeperRenderState> {

    public CreeperGlowLayer(CreeperRenderer<E> renderer) {
        super(renderer, MissingTextureAtlasSprite.getLocation());
    }

    @Override
    public void submitRenderTask(@NotNull RenderPassInfo<@NotNull CreeperRenderState> renderPassInfo, @NotNull SubmitNodeCollector renderTasks) {
        super.submitRenderTask(renderPassInfo, renderTasks);
        final int packedLight = renderPassInfo.packedLight();
//        renderTasks.submit
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull CreeperRenderState renderState) {
        Identifier texture = getTextureResource(renderState);

//        if(renderState.appearsGlowing()) {
        float f = renderState.swelling;
        if (f > 0f || renderState.isPowered) {
            if(renderState.isPowered) f = 1f;
            Identifier glowingTexture = renderState.type.glowingTexture().apply(renderState.baseCreeper);
        }
        return super.getRenderType(renderState);
    }

    public void render(PoseStack poseStack, CreeperRenderState renderState, MultiBufferSource bufferSource, float partialTick, int packedLight) {
        float f = renderState.swelling;
        if (f > 0f || renderState.isPowered) {
            if (renderState.isPowered) f = 1f;

            CreeperType type = renderState.type;

            VertexConsumer glowConsumer = bufferSource.getBuffer(RenderTypes.getTransparentEyes(type.glowingTexture().apply(renderState)));

            getRenderer().reRender(
                    getDefaultBakedModel(renderState), poseStack, bufferSource, renderState,
                    RenderTypes.getTransparentEyes(type.glowingTexture().apply(renderState.baseCreeper)), glowConsumer,
                    partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFF | Mth.ceil(f * 240) << 24
            );
        }
    }
}
