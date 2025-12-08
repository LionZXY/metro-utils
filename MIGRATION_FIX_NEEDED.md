# Migration Fix Needed

## Summary

The migration to Metro 0.6.10 with Kotlin 2.2.21 is **almost complete**, but there's one remaining issue with code
generation.

## What Works

- ✅ Updated Kotlin to 2.2.21
- ✅ Updated KSP to 2.2.21-2.0.4
- ✅ Updated Metro DI to 0.6.10
- ✅ Fixed all dependency references (metro-runtime, no compiler-api needed)
- ✅ Annotations compile successfully
- ✅ Compiler module compiles successfully
- ✅ KSP processor runs and generates code

## What Needs Fixing

### Metro's `@ContributesBinding` doesn't accept Dagger's `@AssistedFactory`

**Error:**

```
`@ContributesBinding` is only applicable to constructor-injected classes, assisted factories, or objects. 
Ensure me.gulya.metro.utils.sample.DefaultTestApi_AssistedFactory is injectable or a bindable object.
```

**Current Generated Code:**

```kotlin
@ContributesBinding(SampleScope::class, binding<TestApi.Factory>())
@AssistedFactory
public abstract class DefaultTestApi_AssistedFactory : TestApi.Factory {
  abstract override fun create(@Assisted("arg1") arg: Int, @Assisted("arg2") arg1: String): DefaultTestApi
}
```

**Problem:**
Metro's `@ContributesBinding` validation doesn't recognize Dagger's `@AssistedFactory` as making a class "injectable".
Metro likely expects its own assisted injection mechanism.

### Possible Solutions

1. **Generate a Module with @Binds** (Recommended)
   Instead of using `@ContributesBinding` directly on the factory, generate a companion module:
   ```kotlin
   @AssistedFactory
   abstract class DefaultTestApi_AssistedFactory : TestApi.Factory {
     abstract override fun create(...): DefaultTestApi
   }
   
   @ContributesTo(SampleScope::class)
   interface DefaultTestApi_AssistedFactoryModule {
     @Binds
     fun bindFactory(impl: DefaultTestApi_AssistedFactory): TestApi.Factory
   }
   ```

2. **Use Metro's Assisted Injection** (If Available)
   Check if Metro has its own assisted injection mechanism that works with `@ContributesBinding`.

3. **Use `@ContributesIntoMap`** (Alternative)
   If binding to a map instead of direct binding works:
   ```kotlin
   @ContributesIntoMap(SampleScope::class)
   @AssistedFactoryKey(TestApi.Factory::class)
   @AssistedFactory
   abstract class DefaultTestApi_AssistedFactory : TestApi.Factory
   ```

4. **Generate an Object with @Provides** (Workaround)
   ```kotlin
   @ContributesBinding(SampleScope::class)
   object DefaultTestApi_AssistedFactoryProvider {
     @Provides
     fun provideFactory(factory: DefaultTestApi_AssistedFactory): TestApi.Factory = factory
   }
   ```

## Files to Modify

- `compiler/src/main/kotlin/me/gulya/metro/utils/CreateAssistedFactory.kt` - Update code generation strategy

## Testing Commands

After fixing:

```bash
./gradlew clean build
./gradlew test
```

## Additional Notes

- The warning "Opt-in requirement marker dev.zacsweers.metro.ExperimentalMetroApi is unresolved" can be fixed by adding
  Metro runtime to dependencies properly
- The warning "Flag is not supported by this version of the compiler: -Xextended-compiler-checks" is benign and can be
  removed from sample build files
