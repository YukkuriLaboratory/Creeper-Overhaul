# Task Plan: Upgrade Mod to Minecraft 1.21.11 Fabric

**Goal**: Upgrade Creeper Overhaul from Minecraft 1.21.1 to 1.21.11 Fabric

**Current State**: Mod is on 1.21.1 with Fabric API 0.116.8, GeckoLib 4.7
**Target State**: Mod on 1.21.11 with Fabric API 0.139.5, GeckoLib 5.4

---

## Task List

### Phase 1: Update Build Configuration
- [x] **Task 1.1**: Update `gradle/libs.versions.toml`
  - Updated minecraft version to "1.21.11"
  - Updated fabric-api to "0.139.5+1.21.11"
  - Updated fabric-loader to "0.18.1"
  - Updated geckolib to "5.4"

- [x] **Task 1.2**: Update `gradle.properties`
  - Updated minecraft_version to "1.21.11"
  - Updated fabric_api_version to "0.139.5+1.21.11"
  - Updated loader_version to "0.18.1"
  - Updated loom_version to "1.14"

- [x] **Task 1.3**: Update `build.gradle`
  - Changed GeckoLib dependency from "geckolib-fabric-1.21.1:4.7" to "geckolib-fabric-1.21.11:5.4"

- [x] **Task 1.4**: Update `src/main/resources/fabric.mod.json`
  - Updated fabricloader dependency to ">=0.18.1"
  - Updated minecraft dependency to "~1.21.11"

### Phase 2: Clean Build and Initial Verification
- [ ] **Task 2.1**: Run `./gradlew clean` to clean build artifacts
- [ ] **Task 2.2**: Run `./gradlew --refresh-dependencies` to update dependencies
- [ ] **Task 2.3**: Run `./gradlew build` to compile and check for errors
- [ ] **Task 2.4**: Document any compilation errors or deprecation warnings

### Phase 3: Fix Code Issues
- [ ] **Task 3.1**: Fix GeckoLib 5.x API changes
  - Review `ReplacedCreeperModel.java` for model loading changes
  - Review `CosmeticRenderer.java` for rendering changes
  - Update any changed method signatures

- [ ] **Task 3.2**: Fix Biome/Spawn related changes
  - Review `ModSpawns.java` for Environment Attributes API changes
  - Verify spawn registration still works correctly

- [ ] **Task 3.3**: Fix any Game Rule changes (if applicable)
  - Review `CreepersConfig.java` for game rule usage
  - Update to new Registry-based system if needed

- [ ] **Task 3.4**: Fix Mixin issues
  - Review all mixin files for mapping changes
  - Update any broken method/field references

### Phase 4: Testing
- [ ] **Task 4.1**: Run datagen
  - Execute `./gradlew runDatagen`
  - Verify data generates without errors

- [ ] **Task 4.2**: Test client launch
  - Execute `./gradlew runClient`
  - Verify game launches without crashes
  - Check console for errors

- [ ] **Task 4.3**: In-game verification
  - Verify all creeper variants spawn correctly
  - Verify models and textures render
  - Verify animations work (GeckoLib)
  - Verify configuration loads
  - Verify cosmetic system works

### Phase 5: Final Verification
- [ ] **Task 5.1**: Run full build
  - Execute `./gradlew clean build`
  - Verify no errors or warnings

- [ ] **Task 5.2**: Verify artifacts
  - Check `build/libs/` for output JAR
  - Verify mod loads in test environment

---

## Notes

### Key Version Changes
- Minecraft: 1.21.1 → 1.21.11
- Fabric API: 0.116.8+1.21.1 → 0.139.5+1.21.11
- GeckoLib: 4.7 → 5.4 (major version)
- Fabric Loader: 0.18.4 → 0.18.1

### Potential Breaking Changes
1. GeckoLib 5.x has API changes from 4.7
2. Game Rules refactored to use Registry
3. Environment Attributes for biome modifications
4. World Render Events reintroduced

### Dependencies to Verify
- GeckoLib 5.4 (confirmed compatible)
- ResourcefulLib (check for 1.21.11 version)
- ResourcefulConfig (check for 1.21.11 version)
- ByteCodecs (verify compatibility)
- Resourceful Cosmetics 4J (verify compatibility)

### Critical Info
- 1.21.11 is the **last obfuscated version** before 26.1
- Already using Mojang mappings - good for future migration
- Will need recompilation for 26.1 (no intermediary compatibility)

---

## Execution Log

*Update this section as tasks are completed*

