package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperModel<E extends BaseCreeper> extends GeoModel<E> {

    private final CreeperType type;

    public CreeperModel(CreeperType type) {
        this.type = type;
    }

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        if (!(geoRenderState instanceof CreeperRenderState creeperRenderState)) {
            return Identifier.withDefaultNamespace("none");
        }
        return type.isShearable() && creeperRenderState.isSheared && type.shearedModel() != null 
            ? type.shearedModel().apply(creeperRenderState.baseCreeper) 
            : type.model().apply(creeperRenderState.baseCreeper);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        if (!(geoRenderState instanceof CreeperRenderState creeperRenderState)) {
            return Identifier.withDefaultNamespace("none");
        }
        return type.texture().apply(creeperRenderState.baseCreeper);
    }

    @Override
    public Identifier getAnimationResource(E entity) {
        return type.animation().apply(entity);
    }
}
