# Renderer Migration Plan: 1.21.1 to 1.21.11

## Current System Overview

### Files Involved
1. `RenderTypes.java` - Custom shader rendering
2. `CreeperRenderer.java` - Entity renderer
3. `CreeperGlowLayer.java` - Glow effect layer
4. `CreeperPowerLayer.java` - Charged creeper effect
5. `CosmeticButton.java` - GUI button rendering
6. `CosmeticLayer.java` - Cosmetic rendering
7. `ReplacedCreeperRenderer.java` - Replaced creeper renderer
8. `ReplacedCreeperGlowLayer.java` - Replaced creeper glow
9. `ReplacedCreeperPowerLayer.java` - Replaced creeper power
10. `rendertype_energy_swirl.json` - Shader file
11. `CreepersClientFabric.java` - Client initialization

---

## Current Implementation

### RenderTypes.java (Lines 29-60)
```java
// Current - getSwirl method
public static RenderType getSwirl(ResourceLocation location, float u, float return create( v) {
   ENERGY_SWIRL_RENDERTYPE.toString(),
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

// Current - getTransparentEyes method
public static RenderType getTransparentEyes(ResourceLocation location) {
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
```

### Shader Registration (Line 21-23)
```java
public static void registerShaders(Registrar registrar) {
    registrar.register(ENERGY_SWIRL_RENDERTYPE, DefaultVertexFormat.NEW_ENTITY, RenderTypes::setEnergyShader);
}
```

---

## Changes Required

### 1. ResourceLocation → Identifier
All `ResourceLocation` → `Identifier` in:
- `RenderTypes.java` - lines 14, 29, 48, 63
- All renderer classes using these methods

### 2. RenderType → RenderTypes
Change these calls:
- `RenderType.entityTranslucent()` → `RenderTypes.entityTranslucent()`

**Files:**
- `CreeperRenderer.java:58` - `return RenderType.entityTranslucent(texture);`
- `CosmeticButton.java:61` - `RenderType.type = RenderType.entityTranslucent(...)`
- `CosmeticLayer.java:57` - `RenderType.type = RenderType.entityTranslucent(...)`
- `ReplacedCreeperRenderer.java:56` - `return RenderType.entityTranslucent(texture);`

### 3. Custom Shader Rendering - COMPLETE REWRITE

The entire `RenderTypes.java` needs rewriting. According to the primer:

#### NEW API Components:
- `RenderSetup` - replaces `CompositeState`
- `RenderPipelines` - replaces direct `RenderType` creation
- `GpuSampler` - new sampler system
- `SamplerCache` - cache for samplers

#### New getSwirl Implementation:
```java
// NEW APPROACH - Need to find equivalent pipeline
public static RenderType getSwirl(Identifier location, float u, float v) {
    // OLD: CompositeState builder pattern
    // NEW: RenderSetup builder pattern
    
    // Need to find: RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL equivalent
    // or create custom pipeline
    
    RenderSetup setup = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT_CULL)
        .withTexture("Sampler0", location, () -> RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
        .useLightmap()
        .useOverlay()
        .setTexturingTransform(new TextureTransform(...))  // For UV animation
        .createRenderSetup();
    
    return RenderType.create("creeperoverhaul:energy_swirl", setup);
}
```

#### New getTransparentEyes Implementation:
```java
public static RenderType getTransparentEyes(Identifier location) {
    RenderSetup setup = RenderSetup.builder(RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL)
        .withTexture("Sampler0", location, () -> RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
        .useLightmap()
        .createRenderSetup();
    
    return RenderType.create("eyes", setup);
}
```

---

## Investigation Needed

### A. Find Equivalent RenderPipelines
Need to find in 1.21.11:
- Which pipeline to use for entity translucent effects
- Which pipeline supports additive transparency
- How to handle NO_CULL

### B. Shader Registration API
Current: `registrar.register(id, format, callback)`
Need to verify if this still works or needs changes

### C. TextureTransform for UV Animation
The `getSwirl` method uses `OffsetTexturingStateShard(u, v)` to animate UVs.
Need to find equivalent: `TextureTransform` in new API

### D. OverlayTexture
`CreeperGlowLayer.java:35` uses `OverlayTexture.NO_OVERLAY`
Verify import/path in 1.21.11

---

## Step-by-Step Implementation Plan

### Step 1: Simple Changes First
1. Update all `ResourceLocation` → `Identifier`
2. Change `RenderType.entityTranslucent()` → `RenderTypes.entityTranslucent()`

### Step 2: Investigate API
After initial build, check:
- Available `RenderPipelines` enum values
- How `TextureTransform` works
- Shader registration compatibility

### Step 3: Rewrite Custom RenderTypes
Based on investigation:
1. Create new `RenderSetup` for swirl effect
2. Create new `RenderSetup` for transparent eyes
3. Handle UV animation differently (may need to modify UV in vertex shader)

### Step 4: Test
- Build project
- Run client
- Verify glow and power layers render correctly

---

## Files to Modify

| File | Changes |
|------|---------|
| `RenderTypes.java` | Complete rewrite, Identifier, RenderSetup |
| `CreeperRenderer.java` | Identifier, RenderTypes.entityTranslucent |
| `CreeperGlowLayer.java` | Identifier (via method calls) |
| `CreeperPowerLayer.java` | Identifier (via method calls) |
| `CosmeticButton.java` | Identifier, RenderTypes.entityTranslucent |
| `CosmeticLayer.java` | Identifier, RenderTypes.entityTranslucent |
| `ReplacedCreeperRenderer.java` | Identifier, RenderTypes.entityTranslucent |
| `ReplacedCreeperGlowLayer.java` | Identifier (via method calls) |
| `ReplacedCreeperPowerLayer.java` | Identifier (via method calls) |
| `CreepersClientFabric.java` | May need shader registration update |

---

## Potential Simplifications

### Option A: Use Vanilla RenderTypes
Instead of custom shaders, consider using:
- `RenderTypes.entityTranslucent()` with texture
- Add alpha-based transparency in the model/texture

### Option B: Simplify getSwirl
If UV animation isn't critical:
- Use static texture with `RenderTypes.entityTranslucent()`
- Lose animated swirl effect

### Option C: Use Additive Transparency
For glow effects:
- Use existing `RenderTypes.emissive()` if available
- Or use `RenderTypes.eyes()` as base

---

## Research Checklist

- [ ] Check `RenderPipelines` enum values in 1.21.11
- [ ] Verify `ShaderInstance` still works or replaced
- [ ] Check if `CoreShaderRegistrationCallback` API changed
- [ ] Find `TextureTransform` usage examples
- [ ] Verify `GpuSampler` / `SamplerCache` API
- [ ] Check `RenderSystem` methods for sampler creation

---

## Risk Assessment

| Risk | Level | Mitigation |
|------|-------|------------|
| Custom shader breaks | HIGH | Use vanilla RenderTypes as fallback |
| UV animation lost | MEDIUM | Accept simplified version |
| GeckoLib compatibility | HIGH | Test early, may need version update |
| Performance impact | LOW | Vanilla types should perform well |
