# Migration from Anvil to Metro - Status

This document tracks the status of migrating this project from Anvil DI to Metro DI.

## Completed ✅

### Core Infrastructure

- [x] Renamed project from `anvil-utils` to `metro-utils`
- [x] Updated `gradle.properties` (group ID, URLs, descriptions)
- [x] Updated `settings.gradle.kts` (root project name)
- [x] Updated `gradle/libs.versions.toml`
    - [x] Changed `anvil` version to `metro` version (0.6.4)
    - [x] Updated library references
    - [x] Updated plugin references
- [x] Updated root `build.gradle.kts`
- [x] Updated `build-logic/conventions` (opt-in annotations)

### Annotations Module

- [x] Moved `annotations/src/main/kotlin/me/gulya/anvil` → `me/gulya/metro`
- [x] Updated package declarations in annotation files
    - [x] `AssistedKey.kt`
    - [x] `ContributesAssistedFactory.kt`
- [x] Updated `annotations/build.gradle.kts` (artifact names, descriptions)

### Compiler Module - Core

- [x] Moved `compiler/src/main/kotlin/me/gulya/anvil` → `me/gulya/metro`
- [x] Updated package declarations in compiler files
    - [x] `ParameterKey.kt`
    - [x] `Errors.kt`
    - [x] `CreateAssistedFactory.kt` (removed Anvil imports, updated to use Metro's `@ContributesBinding`)
- [x] Updated `compiler/build.gradle.kts`
    - [x] Changed Anvil dependencies to Metro dependencies
    - [x] Added Metro runtime dependency
    - [x] Removed Anvil compiler utils dependency

### Compiler Module - KSP

- [x] Updated package declaration in `ContributesAssistedFactorySymbolProcessor.kt`
- [x] Updated imports to use Metro packages
- [x] Updated package declaration in `ErrorLoggingSymbolProcessor.kt`

### Compiler Module - Embedded

- [x] Updated package declarations in `ContributesAssistedFactoryCodeGenerator.kt`
- [x] Updated imports to use Metro packages
- ⚠️ **Note**: Embedded code generator depends on Anvil's compiler API which Metro doesn't have. This will need to be
  removed or significantly refactored.

### Sample Projects

- [x] Moved sample source files from `me.gulya.anvil` to `me.gulya.metro`
- [x] Updated package declarations in sample files
    - [x] `TestApi.kt`
    - [x] `DefaultTestApi.kt`
    - [x] `KspEntrypoint.kt`
- [x] Updated `samples/library/impl/ksp/build.gradle.kts` (Metro plugin)
- [x] Updated `samples/entrypoint/ksp/build.gradle.kts` (Metro plugin, removed KAPT)
- [x] Removed embedded sample modules (Metro doesn't support embedded mode)
- [x] Updated `settings.gradle.kts` to remove embedded sample references

### Documentation

- [x] Updated README.md
    - [x] Project name and description
    - [x] Compatibility table
    - [x] Getting started instructions
    - [x] Module structure documentation
- [x] Updated CHANGELOG.md

## Pending / Issues ⚠️

### Compiler Module - Embedded Generator

The embedded code generator (`ContributesAssistedFactoryCodeGenerator.kt`) heavily depends on Anvil's compiler API:

- `com.squareup.anvil.compiler.api.*`
- `com.squareup.anvil.compiler.internal.*`

**Decision needed**: Since Metro is purely a compiler plugin and doesn't expose a similar API:

1. Remove the embedded code generator entirely
2. Keep it for backward compatibility with Anvil users
3. Refactor to work differently (unlikely to be feasible)

**Recommendation**: Remove it, as Metro users should use KSP exclusively.

### Test Files

Test files need extensive updates:

- [ ] Move test files from `compiler/src/test/kotlin/me/gulya/anvil` to `me/gulya/metro`
- [ ] Update package declarations in all test files
- [ ] Update `ContributesAssistedFactoryCodeGeneratorTest.kt` (uses Anvil test utilities)
- [ ] Update `ContributesAssistedFactorySymbolProcessorCompileTest.kt`
- [ ] Update `KspMultiRoundFunctionalTest.kt`
- [ ] Update test fixtures in `compiler/src/test/fixtures`

Many tests use Anvil's `compileAnvil()` test utility which may not have a Metro equivalent.

### Build Configuration

- [ ] Verify all Gradle dependencies resolve correctly
- [ ] Remove any remaining Anvil-specific configurations
- [ ] Test that the project builds successfully
- [ ] Update dependency guard configurations if present

### Additional Metro-Specific Changes

Based on Metro documentation, consider:

- [ ] Update `@ContributesBinding` usage to Metro's API (uses `binding<Type>()` syntax)
- [ ] Verify Metro's contribution aggregation works as expected
- [ ] Update any Dagger interop if needed
- [ ] Consider Metro-specific features like optional dependencies

## Known Issues

1. **Linter Errors**: Several files currently have unresolved reference errors:
    - `CreateAssistedFactory.kt`: References to `ContributesBinding` (Metro runtime not yet added to dependencies)
    - `ContributesAssistedFactoryCodeGenerator.kt`: References to Anvil compiler API
    - Sample entrypoint: References to Metro's `DependencyGraph` and `createGraph()`

2. **Metro Runtime**: The Metro runtime dependency needs to be properly configured in the build

3. **Test Infrastructure**: Tests that depend on Anvil's compiler testing utilities need migration strategy

## Next Steps

1. **Immediate**:
    - Remove or deprecate embedded code generator
    - Fix remaining linter errors
    - Ensure project builds

2. **Short-term**:
    - Migrate test files to Metro
    - Set up Metro-compatible test infrastructure
    - Update any remaining documentation

3. **Verification**:
    - Test generated code works with Metro
    - Verify assisted factory generation
    - Test contribution aggregation
    - Validate sample projects run correctly

## References

- [Metro Documentation](https://zacsweers.github.io/metro/latest/)
- [Metro GitHub](https://github.com/ZacSweers/metro)
- [Metro Features](https://zacsweers.github.io/metro/latest/features/)
- [Metro Dependency Graphs](https://zacsweers.github.io/metro/latest/dependency-graphs/)
