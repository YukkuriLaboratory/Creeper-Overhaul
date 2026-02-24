package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends LivingEntity {
    protected CreeperMixin(EntityType<@NotNull ? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "explodeCreeper",
            at = @At("RETURN")
    )
    public void onExplode(CallbackInfo ci) {
        var creeper = (Creeper)(Object) this;
        if(!(creeper instanceof BaseCreeper baseCreeper)) return;
        var shear = baseCreeper.type.shearDrop();
        if(shear == null) return;

        if(level() instanceof ServerLevel serverLevel) {
            if(random.nextFloat() < CreepersConfig.tinyCactusDropChance) {
                spawnAtLocation(serverLevel, shear.get(), 1.7F);
            };
        }
    }
}
