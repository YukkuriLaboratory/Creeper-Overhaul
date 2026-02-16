# GeckoLib 4 to GeckoLib 5 Migration Plan

## Summary
Migrate from GeckoLib 4 to GeckoLib 5 (already in build.gradle: 5.4.3)

## Key Changes Required

### 1. GeoModel Changes (CreeperModel.java)
- **Method signature change**: `setCustomAnimations` now uses different parameters
- **Method name changes**:
  - `getModelResource` → `getModelLocation`
  - `getTextureResource` → `getTextureLocation`
- Import changes for AnimationState

### 2. Render Layer Changes
- **GeoRenderLayer**: Method signatures changed in GeckoLib 5
- **TextureLayerGeoLayer**: API changes for glow layers
- Replaced entity render layers need updating

### 3. Render Types (RenderTypes.java)
- `ShaderStateShard` class was removed in newer Minecraft versions
- Need to use alternative approach for energy swirl shader

### 4. Entity API Changes (Separate from GeckoLib)
- NeutralMob interface changes in MC 1.21.1
- CompoundTag API changes
- Various entity method signature changes

---

## Files to Update

### GeckoLib Related
1. `CreeperModel.java` - Update method signatures
2. `CreeperRenderer.java` - May need adjustments
3. `CreeperGlowLayer.java` - Update for GeckoLib 5 layer API
4. `CreeperPowerLayer.java` - Update for GeckoLib 5 layer API
5. `ReplacedCreeperRenderer.java` - Update for replaced entity API
6. `ReplacedCreeperGlowLayer.java` - Update layer
7. `ReplacedCreeperPowerLayer.java` - Update layer
8. `ReplacedCreeperModel.java` - Check for updates
9. `RenderTypes.java` - Fix ShaderStateShard issue

### Entity API (Not GeckoLib but needed for build)
10. `NeutralCreeper.java` - Fix NeutralMob interface
11. `PassiveCreeper.java` - Fix inherited issues
12. Various entity files with CompoundTag changes

---

## Migration Steps

### Step 1: Update CreeperModel.java
- Change `getModelResource` to `getModelLocation`
- Change `getTextureResource` to `getTextureLocation`
- Update `setCustomAnimations` - use new AnimationState from GeckoLib 5

### Step 2: Update RenderLayers
- Update method signatures for render layers
- Use new GeoRenderLayer API

### Step 3: Fix RenderTypes.java
- Replace ShaderStateShard with alternative

### Step 4: Fix Entity API issues
- Update NeutralMob implementations
- Fix CompoundTag read/write methods

---

## References
- https://wiki.geckolib.com/docs/geckolib5/updating/introduction
- https://wiki.geckolib.com/docs/geckolib5/updating/important/conceptual-changes
- https://wiki.geckolib.com/docs/geckolib5/updating/important/animationstate
