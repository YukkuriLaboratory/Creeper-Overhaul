package tech.thatgravyboat.creeperoverhaul.client;

import com.mojang.blaze3d.opengl.GlProgram;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Consumer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class RenderTypes extends RenderType {

    private static GlProgram energySwirlShader;
    public static final Identifier ENERGY_SWIRL_RENDERTYPE = Creepers.id("rendertype_energy_swirl");
    private static final ShaderStateShard ENERGY_SWIRL_SHARD = new ShaderStateShard(() -> energySwirlShader);

    public RenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static void registerShaders(Registrar registrar) {
        registrar.register(ENERGY_SWIRL_RENDERTYPE, DefaultVertexFormat.NEW_ENTITY, RenderTypes::setEnergyShader);
    }

    public static void setEnergyShader(GlProgram shader) {
        RenderTypes.energySwirlShader = shader;
    }

    public static RenderType getSwirl(Identifier location, float u, float v) {
        return create(ENERGY_SWIRL_RENDERTYPE.toString(),
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                CompositeState.builder()
                        .setShaderState(ENERGY_SWIRL_SHARD)
                        .setTextureState(new TextureStateShard(location, false, false))
                        .setTexturingState(new OffsetTexturingStateShard(u, v))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(false)
        );
    }

    public static RenderType getTransparentEyes(Identifier location) {
        return create("eyes",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS, 256,
                false, true,
                CompositeState.builder()
                        .setShaderState(RENDERTYPE_EYES_SHADER)
                        .setTextureState(new TextureStateShard(location, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setWriteMaskState(COLOR_WRITE)
                        .createCompositeState(false)
        );
    }

    public interface Registrar {
        void register(Identifier id, VertexFormat vertexFormat, Consumer<GlProgram> loadCallback);
    }
}
