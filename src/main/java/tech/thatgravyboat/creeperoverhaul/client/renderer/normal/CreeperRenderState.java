package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

@Environment(EnvType.CLIENT)
public class CreeperRenderState extends LivingEntityRenderState {
    public float swelling;
    public boolean isPowered;
    public CreeperType type;
    public boolean isSheared;
    public BaseCreeper baseCreeper;
}

