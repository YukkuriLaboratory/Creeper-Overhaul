# Plan: Update Creeper Overhaul from 1.21.1 to 1.21.11

## Overview
This migration spans 10 Minecraft versions (1.21.1 → 1.21.11) and includes major changes to:
- Package reorganizations
- Rendering system overhaul
- Data components system
- Entity render states
- Naming conventions (ResourceLocation → Identifier)

---

## Phase 1: Version Updates

### 1.1 Update gradle/libs.versions.toml
- Change `minecraft = "1.21.1"` → `"1.21.11"`
- Update `fabric-api` to version compatible with 1.21.11
- Update `geckolib` to latest version for 1.21.11 compatibility (currently 4.7)
- Update other dependencies as needed

### 1.2 Update gradle.properties
- Update `mod_version` if needed

### 1.3 Update fabric.mod.json
- Change `"minecraft": "~1.21.1"` → `"minecraft": "~1.21.11"`

---

## Phase 2: Package & Import Changes (Most Extensive)

### 2.1 ResourceLocation → Identifier (84 occurrences)
All `ResourceLocation` references must change to `Identifier`:
- `net.minecraft.resources.ResourceLocation` → `net.minecraft.resources.Identifier`
- `ResourceLocation.fromNamespaceAndPath()` → `Identifier.withNamespaceAndPath()`
- Update in: Creepers.java, all Renderers, registry files, datagen

**Files affected:**
- Creepers.java
- CreepersLootTableProvider.java
- CosmeticTexture.java
- CosmeticModel.java
- CosmeticGridWidget.java
- CosmeticButton.java
- RenderTypes.java
- CreeperModel.java
- CreeperRenderer.java
- CreeperPlugin.java
- PluginRegistry.java
- CreeperType.java
- CreepersFabric.java
- All renderer classes

### 2.2 critereon → criterion package
- `net.minecraft.advancements.critereon` → `net.minecraft.advancements.criterion`

### 2.3 util package reorganization
- Various utility classes moved to `net.minecraft.util`
- `Mth` remains at `net.minecraft.util.Mth`

### 2.4 TagKey + ResourceLocation (CORNER CASE)
- `CreepersFabric.java:35-36` uses `TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(...))`
- Must change to `TagKey.create(Registries.BIOME, Identifier.withNamespaceAndPath(...))`

### 2.5 client.model subpackages
Models moved to new locations:
- `CreeperModel` → `.monster.creeper.CreeperModel`
- Other entity models similarly reorganized

### 2.6 world.entity subpackages
Entities reorganized:
- `net.minecraft.world.entity.monster.Creeper` → still in place but verify imports

---

## Phase 3: Rendering System Changes (HIGH RISK)

### 3.1 RenderType → RenderTypes (4+ occurrences)
- `RenderType.entityTranslucent()` → `RenderTypes.entityTranslucent()`
- Move existing types from `RenderType` to `RenderTypes`

**Files affected:**
- CreeperRenderer.java (line 58)
- CosmeticButton.java (line 61)
- CosmeticLayer.java (line 57)
- ReplacedCreeperRenderer.java (line 56)

### 3.2 Custom Shader Rendering (CRITICAL CORNER CASE)
- `RenderTypes.java` uses custom shader rendering
- Methods: `getSwirl()`, `getTransparentEyes()`
- Uses `RenderType` constructor directly
- **Action**: Complete rewrite needed using new `RenderSetup` API

```java
// NEW API format (from primer):
public static final RenderSetup EXAMPLE_SETUP = RenderSetup.builder(
    RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL
)
    .withTexture("Sampler0", Identifier.withDefaultNamespace("textures/..."), 
                 () -> RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
    .useLightmap()
    .useOverlay()
    .createRenderSetup();

public static final RenderType EXAMPLE_TYPE = RenderType.create("examplemod:example_type", EXAMPLE_SETUP);
```

### 3.3 Render Pipelines
- `RenderPipelines.SOLID` → `SOLID_BLOCK`, `SOLID_TERRAIN`
- Similar changes for CUTOUT, TRANSLUCENT, TRIPWIRE

### 3.4 GUI Rendering
- `GuiGraphics` changes with prepare/render phases
- Dialog system additions

---

## Phase 4: Data Components System

### 4.1 Tool Materials
- `Tier` → `ToolMaterial`
- `TieredItem` removed - items now use `ToolMaterial` directly

### 4.2 Armor Materials
- `ArmorMaterial` → record format
- New equipment JSON format

### 4.3 Other Components
- `DataComponents.UNBREAKABLE` is now `Unit` instance
- Various component changes for items

---

## Phase 5: Entity & Interaction Changes

### 5.1 InteractionResult
- Now uses sealed implementations
- Current usage `InteractionResult.sidedSuccess()` may need review

### 5.2 Equipment System
- `EquipmentSlot` changes
- `Equippable` component changes

---

## Phase 6: Mixin Updates (CORNER CASE)

### 6.1 Verify Mixin Targets
- `BaseCreeperMixin` - parent class `Creeper` moved to new package
- `LivingEntityRendererInvoker` - class moved to different package
- `EntityRenderDispatcher` - API changed

### 6.2 EntityRenderDispatcherMixin
- Uses `ResourceManager` which may have changed
- `onResourceManagerReload` method signature may have changed

---

## Phase 7: Other Corner Cases

### 7.1 TextureUtil
- `CosmeticTexture.java:84` uses `TextureUtil.prepareImage()`
- Verify method signature changes

### 7.2 OverlayTexture
- `CreeperGlowLayer.java` uses `OverlayTexture.NO_OVERLAY`
- Verify import/path changes

### 7.3 GeckoLib Compatibility
- May need to update GeckoLib version for 1.21.11
- GeoEntityRenderer API may have changed

---

## Phase 8: Build & Verify

### 8.1 Run Initial Build
```bash
./gradlew clean build
```

### 8.2 Fix Compilation Errors
- Address any remaining compilation errors from imports/namespaces

### 8.3 Test In-Game
```bash
./gradlew runClient
```

### 8.4 Run Datagen if Needed
```bash
./gradlew runDatagen
```

---

## Priority Order for Implementation

1. Update version numbers (Phase 1)
2. Fix ResourceLocation → Identifier (Phase 2.1)
3. Fix TagKey + Identifier (Phase 2.4)
4. Fix other imports (Phase 2.2-2.6)
5. Fix rendering - RenderType.entityTranslucent (Phase 3.1)
6. **REWRITE Custom Shader Rendering** (Phase 3.2 - CRITICAL)
7. Fix data components (Phase 4)
8. Fix entity changes (Phase 5)
9. Verify/fix mixins (Phase 6)
10. Fix other corner cases (Phase 7)
11. Build and verify (Phase 8)

---

## Estimated Files to Modify

- gradle/libs.versions.toml
- fabric.mod.json
- ~20+ Java source files with major changes
- ~60+ Java files with import changes
- Complete rewrite: RenderTypes.java
- Verify: 6 mixin files

---

## Key Risks

1. **Custom RenderTypes.java** - Most complex, requires complete rewrite
2. **GeckoLib compatibility** - May break entity rendering
3. **Mixin targets** - Could fail if parent classes moved
4. **Shader registration** - API likely changed completely
