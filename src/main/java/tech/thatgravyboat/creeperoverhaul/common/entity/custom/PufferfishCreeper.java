package tech.thatgravyboat.creeperoverhaul.common.entity.custom;

import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.WaterCreeper;

public class PufferfishCreeper extends WaterCreeper {

    private static final List<EntityDimensions> DIMENSIONS = List.of(
            EntityDimensions.scalable(0.6875f, 1.125f),
            EntityDimensions.scalable(0.625f, 0.9375f),
            EntityDimensions.scalable(1.1f, 1.375f)
    );

    private static final EntityDataAccessor<Byte> VARIANT = SynchedEntityData.defineId(PufferfishCreeper.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> PUFF_STATE = SynchedEntityData.defineId(PufferfishCreeper.class, EntityDataSerializers.BYTE);

    private int inflateCounter;
    private int deflateTimer;

    public PufferfishCreeper(EntityType<? extends PufferfishCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    public static EntityType.EntityFactory<PufferfishCreeper> ofPufferfish(CreeperType type) {
        return (entityType, level) -> new PufferfishCreeper(entityType, level, type);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, net.minecraft.world.entity.EntitySpawnReason type, @Nullable SpawnGroupData data) {
        setVariant(level.getRandom().nextBoolean() ? Variant.TEAL : Variant.BROWN);
        return super.finalizeSpawn(level, difficulty, type, data);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PuffGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, (byte) 0);
        builder.define(PUFF_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(net.minecraft.network.syncher.EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
        }
    }

    public byte getPuffState() {
        return this.entityData.get(PUFF_STATE);
    }

    public void setPuffState(int puffState) {
        this.entityData.set(PUFF_STATE, (byte) puffState);
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, (byte) variant.ordinal());
    }

    public int getPuffId() {
        return switch (this.getPuffState()) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 3;
        };
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            if (this.inflateCounter > 0) {
                if (this.getPuffState() == 0) {
                    this.setPuffState(1);
                } else if (this.inflateCounter > 40 && this.getPuffState() == 1) {
                    this.setPuffState(2);
                }

                ++this.inflateCounter;
            } else if (this.getPuffState() != 0) {
                if (this.deflateTimer > 60 && this.getPuffState() == 2) {
                    this.setPuffState(1);
                } else if (this.deflateTimer > 100 && this.getPuffState() == 1) {
                    this.setPuffState(0);
                }

                ++this.deflateTimer;
            }
        }

        super.tick();
    }

    @Override
    public boolean canSwell() {
        return false;
    }

    @Override
    public @org.jetbrains.annotations.NotNull EntityDimensions getDefaultDimensions(@org.jetbrains.annotations.NotNull Pose pose) {
        return DIMENSIONS.get(this.getPuffId() - 1);
    }

    public enum Variant {
        TEAL,
        BROWN;

        public static Variant byId(byte id) {
            return id == 0 ? TEAL : BROWN;
        }
    }

    public static class PuffGoal extends Goal {
        private final PufferfishCreeper creeper;

        public PuffGoal(PufferfishCreeper creeper) {
            this.creeper = creeper;
        }

        @Override
        public boolean canUse() {
            return false;
        }

        @Override
        public void start() {
            this.creeper.inflateCounter = 1;
            this.creeper.deflateTimer = 0;
        }

        @Override
        public void stop() {
            this.creeper.inflateCounter = 0;
        }
    }
}
