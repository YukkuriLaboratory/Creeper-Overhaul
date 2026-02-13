package tech.thatgravyboat.creeperoverhaul.api;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public interface CreeperPlugin {

    Identifier id();

    default boolean canAttack(LivingEntity entity) {
        return true;
    }

    default boolean isAfraidOf(BaseCreeper creeper, LivingEntity entity) {
        return false;
    }
}
