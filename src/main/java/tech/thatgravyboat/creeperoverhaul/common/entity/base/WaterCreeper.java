package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.state.AnimationTest;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.WaterCreeperMoveControl;
import tech.thatgravyboat.creeperoverhaul.common.utils.AnimationConstants;

public class WaterCreeper extends BaseCreeper {

    public WaterCreeper(EntityType<? extends Creeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new WaterCreeperMoveControl(this);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0, 40));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (this.isAlive() && !this.isInWater()) {
            this.setAirSupply(i - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(level().damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 0.5, 0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vec3);
        }
    }

    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setOnGround(false);
            this.push((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F);
            this.playSound(type.getFlopSound(this).orElse(SoundEvents.COD_FLOP), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected PlayState animate(AnimationTest<@NotNull BaseCreeper> state) {
//        RawAnimation animation = state
        if (isAttacking()) {
            state.setAnimation(AnimationConstants.ATTACK);
            return PlayState.CONTINUE;
//        } else if (animation != null && animation.animation().name().equals("animation.creeper.attack") && event.getController().getAnimationState().equals(AnimationController.State.RUNNING)) {
//            return PlayState.CONTINUE;
        } else if (!isInWater()) {
            state.setAnimation(AnimationConstants.FLOP);
            return PlayState.CONTINUE;
        } else if (state.isMoving()) {
            state.setAnimation(AnimationConstants.SWIM);
            return PlayState.CONTINUE;
        } else {
            state.setAnimation(AnimationConstants.IDLE);
        }
        return PlayState.STOP;
    }
}
