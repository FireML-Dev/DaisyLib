## DaisyLib

Yet another library to make Minecraft plugin development easier.

### Library

#### Gradle (Kotlin)
```kotlin
repositories {
    maven("https://repo.codemc.io/repository/FireML/")
}

dependencies {
    compileOnly("uk.firedev:DaisyLib:4.0-SNAPSHOT")
}
```

### Plugin

The plugin version can be obtained from the Jenkins Server: https://ci.codemc.io/job/FireML/job/DaisyLib/.

Unlike the library, the plugin is always built against the latest Minecraft version.
