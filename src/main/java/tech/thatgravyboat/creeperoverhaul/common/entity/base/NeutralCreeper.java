package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperMeleeAttackGoal;

public class NeutralCreeper extends BaseCreeper implements NeutralMob {

    private static final EntityDataAccessor<@NotNull Long> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(NeutralCreeper.class, EntityDataSerializers.LONG);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @Nullable
    private EntityReference<@NotNull LivingEntity> persistentTarget;

    public NeutralCreeper(EntityType<? extends @NotNull NeutralCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        addPersistentAngerSaveData(valueOutput);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        readPersistentAngerSaveData(this.level(), valueInput);
    }

    @Override
    public void aiStep() {
        if (this.level() instanceof ServerLevel serverLevel) {
            this.updatePersistentAnger(serverLevel, false);
        }
    }

    @Override
    protected void registerAttackGoals() {
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, this::isAngryAt));
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        if (!PluginRegistry.getInstance().canAttack(this, entity)) {
            return false;
        }
        return super.canAttack(entity);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0L);
    }

    @Override
    public boolean canSwell() {
        return super.canSwell() && (this.isAngry() || isIgnited());
    }

    @Override
    public long getPersistentAngerEndTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setPersistentAngerEndTime(long l) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, l);
    }


    @Override
    public @Nullable EntityReference<@NotNull LivingEntity> getPersistentAngerTarget() {
        return persistentTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<@NotNull LivingEntity> entityReference) {
        persistentTarget = entityReference;
    }

    @Override
    public void startPersistentAngerTimer() {
        setPersistentAngerEndTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
