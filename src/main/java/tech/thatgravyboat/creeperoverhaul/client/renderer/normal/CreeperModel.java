package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.resources.Identifier;
import software.bernie.geckolib.constant.DataTickets;
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
        if(!(geoRenderState instanceof CreeperRenderState creeperRenderState)) return Identifier.withDefaultNamespace("none");
        return type.isShearable() && creeperRenderState.isSheared && type.shearedModel() != null ? type.shearedModel().apply(creeperRenderState.baseCreeper) : type.model().apply(creeperRenderState.baseCreeper);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        if(!(geoRenderState instanceof CreeperRenderState creeperRenderState)) return Identifier.withDefaultNamespace("none");
        return type.texture().apply(creeperRenderState.baseCreeper);
    }

    @Override
    public Identifier getAnimationResource(E entity) {
        return type.animation().apply(entity);
    }

    @Override
    public void setCustomAnimations(E animatable, long instanceId, AnimationState<E> state) {
        super.setCustomAnimations(animatable, instanceId, state);

        EntityModelData extraDataOfType = state.getData(DataTickets.ENTITY_MODEL_DATA);
        var head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotY(extraDataOfType.netHeadYaw() * ((float)Math.PI / 180F));
        }
    }
}
