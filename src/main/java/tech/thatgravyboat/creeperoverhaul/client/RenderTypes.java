package tech.thatgravyboat.creeperoverhaul.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypes;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.Identifier;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class RenderTypes extends RenderType {

    private static ShaderInstance energySwirlShader;
    public static final Identifier ENERGY_SWIRL_RENDERTYPE = Creepers.id("rendertype_energy_swirl");

    public RenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static void registerShaders(Registrar registrar) {
        registrar.register(ENERGY_SWIRL_RENDERTYPE, DefaultVertexFormat.NEW_ENTITY, RenderTypes::setEnergyShader);
    }

    public static void setEnergyShader(ShaderInstance shader) {
        RenderTypes.energySwirlShader = shader;
    }

    public static RenderType getSwirl(Identifier location, float u, float v) {
        return RenderTypes.entityTranslucent(location);
    }

    public static RenderType getTransparentEyes(Identifier location) {
        return RenderTypes.entityTranslucent(location);
    }

    public interface Registrar {
        void register(Identifier id, VertexFormat vertexFormat, Consumer<ShaderInstance> loadCallback);
    }
}
