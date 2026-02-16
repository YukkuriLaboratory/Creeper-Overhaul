package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import java.util.Locale;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

public class ReplacedCreeperModel<E extends ReplacedCreeper> extends GeoModel<E> {

    private static final Identifier ANIMATION = Creepers.id("animations/creeper.animation.json");

    @Override
    @NonNull
    public Identifier getModelResource(@NonNull GeoRenderState geoRenderState) {
        if (Creepers.EVENT == Events.NONE) {
            return Creepers.id("plains");
        } else {
            String id = Creepers.EVENT.name().toLowerCase(Locale.ROOT);
            return Creepers.id("events/" + id);
        }
    }

    @Override
    @NonNull
    public Identifier getTextureResource(@NonNull GeoRenderState geoRenderState) {
        if (Creepers.EVENT == Events.NONE) {
            return Creepers.id("textures/entity/plains/plains_creeper.png");
        } else {
            String id = Creepers.EVENT.name().toLowerCase(Locale.ROOT);
            return Creepers.id("textures/entity/events/" + id + ".png");
        }
    }

    @Override
    @NonNull
    public Identifier getAnimationResource(ReplacedCreeper animatable) {
        return ANIMATION;
    }
}
