package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class NeutralCreeper extends BaseCreeper {

    public NeutralCreeper(EntityType<? extends NeutralCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    @Override
    public boolean canSwell() {
        return super.canSwell() && (this.isIgnited());
    }
}
