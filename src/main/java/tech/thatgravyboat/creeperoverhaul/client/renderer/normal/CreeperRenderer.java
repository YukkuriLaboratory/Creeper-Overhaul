package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<@NotNull E, @NotNull CreeperRenderState> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
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
    public @org.jspecify.annotations.Nullable RenderType getRenderType(CreeperRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}
