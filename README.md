# Metro Utils

Metro Utils is a library that provides a set of annotations to simplify the development of modular applications with
Metro DI.

## Features

- `@ContributesAssistedFactory` - automatically generates assisted factories for annotated classes and contributes them
  to the specified scope as bindings of the provided factory type.

## Compatibility Map

| Metro Utils Version | Metro Version | Plugin ID           |
|---------------------|---------------|---------------------|
| 0.4.0               | 0.6.4+        | dev.zacsweers.metro |

## Getting Started

1. Ensure you have at least `0.6.4` version of Metro DI in your project.
2. Ensure you have `ksp` plugin applied to your project:
    ```kotlin
    plugins {
        id("com.google.devtools.ksp")
    }
    ```

3. Add the following dependencies to your project:
    ```kotlin
    dependencies {
        implementation("me.gulya.metro:annotations:0.4.0")
        ksp("me.gulya.metro:compiler:0.4.0")
    }
    ```

4. Enable Metro in your project by applying the Metro Gradle plugin:
    ```kotlin
    plugins {
        id("dev.zacsweers.metro") version "0.6.4"
    }
    ```

## @ContributesAssistedFactory

`@ContributesAssistedFactory` is an annotation that helps to automatically generate
assisted factories for annotated classes and contribute them to the specified scope
as bindings of the provided factory type.

### Motivation

When building modular applications with Metro DI, it's common to define an API module with public interfaces
and a separate implementation module with concrete classes. Assisted injection is a useful pattern for creating
instances of classes with a mix of dependencies provided by Metro and runtime parameters.
However, using Metro's @AssistedFactory requires the factory interface and the implementation
class to be in the same module, which breaks the separation between API and implementation.

`@ContributesAssistedFactory` solves this problem by allowing to declare the bound type (factory interface) in the
API module and generate the actual factory implementation in the implementation module.

### Usage

1. Define your bound type (factory interface) in the API module:

```kotlin
interface MyClass

interface MyClassFactory {
    fun create(param1: String, param2: Int): MyClass
}
```

2. Annotate your implementation class with `@ContributesAssistedFactory` in the implementation module:

```kotlin
@ContributesAssistedFactory(AppScope::class, MyClassFactory::class)
class DefaultMyClass @Inject constructor(
    @Assisted param1: String,
    @Assisted param2: Int
) : MyClass
```

3. The following factory will be generated, implementing MyClassFactory:

```kotlin
@ContributesBinding(AppScope::class, MyClassFactory::class)
@AssistedFactory
interface DefaultMyClass_AssistedFactory : MyClassFactory {
    override fun create(param1: String, param2: Int): DefaultMyClass
}
```

### Module structure in the project

- `:compiler` - contains KSP code generators
    - Package `me.gulya.metro.utils.ksp` - KSP code generators
- `:annotations` - contains annotations supported by this code generator
- `:samples` - sample project with examples of usage
    - `:samples:entrypoint` - entrypoint modules showcasing usage of KSP code generators.
        - `:samples:ksp` - entrypoint module where component merging is done. Depends on KSP implementation module.
    - `:samples:library` - library modules using this code generator.
        - `:samples:library:api` - API module with factory interfaces.
        - `:samples:library:impl:ksp` - Implementation module using KSP code generator.

### Important notes

- The factory interface method parameters should be annotated with @AssistedKey instead of @Assisted to distinguish
  factory method parameters from constructor parameters.