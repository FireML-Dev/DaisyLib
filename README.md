## DaisyLib

> [!WARNING]  
> This library is not stable, and things may change at any time.

A collection of classes for easier use of Paper's API.

https://ci.codemc.io/job/FireML/job/DaisyLib/

#### Versioning

Library versions are handled like so:
- MAJOR - Changed when a massive part of the plugin is reworked
- MINOR - When a new minecraft update is released, also the time for breaking changes to be made.

#### Gradle (Kotlin)
```kotlin
repositories {
    maven("https://repo.codemc.io/repository/FireML/")
}

dependencies {
    compileOnly("uk.firedev:DaisyLib:3.0-SNAPSHOT")
}
```
