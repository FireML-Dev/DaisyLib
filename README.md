## DaisyLib

> [!WARNING]  
> This library is nowhere near stable, and things may change at any time.

A collection of classes for easier use of Paper's API.

https://ci.firedev.uk/job/DaisyLib/

[![CodeFactor](https://www.codefactor.io/repository/github/fireml-dev/daisylib/badge)](https://www.codefactor.io/repository/github/fireml-dev/daisylib)

#### Versioning

Starting with Version 2.0.4-SNAPSHOT, versioning is handled like so:
- MAJOR - Changed when a massive part of the plugin is reworked
- MINOR - When a new minecraft update is released, also the time for breaking changes to be made.
- PATCH - When a new feature is added or a non-breaking change is made.

#### Gradle (Kotlin)
```kotlin
repositories {
    maven("https://repo.codemc.io/repository/FireML/")
}

dependencies {
    compileOnly("uk.firedev:DaisyLib:2.3.0-SNAPSHOT")
}
```
