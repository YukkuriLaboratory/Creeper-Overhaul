package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BaseCreeper extends Creeper implements GeoEntity {

    private static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING =
            SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SHEARED =
            SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // GeckoLib5 Animations
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.creeper.idle");
    private static final RawAnimation WALK =
            RawAnimation.begin().thenLoop("animation.creeper.walk");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.creeper.attack");

    public final CreeperType type;

    public static EntityType.EntityFactory<@NotNull BaseCreeper> of(CreeperType type) {
        return (entityType, level) -> new BaseCreeper(entityType, level, type);
    }

    public static EntityType.EntityFactory<@NotNull PassiveCreeper> ofPassive(CreeperType type) {
        return (entityType, level) -> new PassiveCreeper(entityType, level, type);
    }

    public static EntityType.EntityFactory<@NotNull NeutralCreeper> ofNeutral(CreeperType type) {
        return (entityType, level) -> new NeutralCreeper(entityType, level, type);
    }

    public BaseCreeper(EntityType<? extends @NotNull Creeper> entityType, Level level, CreeperType type) {
        super(entityType, level);
        this.type = type;
    }

    // -----------------------------
    // GeckoLib5 Animation Section
    // -----------------------------

    protected PlayState animate(AnimationTest<@NotNull BaseCreeper> state) {
        if (isAttacking()) {
            state.setAnimation(ATTACK);
        } else if (state.isMoving()) {
            state.setAnimation(WALK);
        } else {
            state.setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>("controller", 3, this::animate)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // -----------------------------
    // Synced Data
    // -----------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_ATTACKING, false);
        builder.define(DATA_IS_SHEARED, false);
    }

    public boolean isAttacking() {
        return this.getEntityData().get(DATA_IS_ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.getEntityData().set(DATA_IS_ATTACKING, attacking);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_IS_SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.getEntityData().set(DATA_IS_SHEARED, sheared);
    }

    public boolean canSwell() {
        return isExplodingCreeper();
    }

    public boolean isExplodingCreeper() {
        return this.type.melee() == 0;
    }

    // -----------------------------
    // Combat
    // -----------------------------

    @Override
    public boolean doHurtTarget(@NotNull ServerLevel serverLevel, @NotNull Entity entity) {
        setAttacking(true);
        boolean result = super.doHurtTarget(serverLevel, entity);
        setAttacking(false);
        return result;
    }

    // -----------------------------
    // Sounds
    // -----------------------------

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return type.getHurtSound(this).orElseGet(() -> super.getHurtSound(source));
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return type.getDeathSound(this).orElseGet(super::getDeathSound);
    }
}
