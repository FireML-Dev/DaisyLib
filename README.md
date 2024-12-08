## DaisyLib

A collection of classes for easier use of Paper's API.

https://ci.firedev.uk/job/DaisyLib/

[![CodeFactor](https://www.codefactor.io/repository/github/fireml-dev/daisylib/badge)](https://www.codefactor.io/repository/github/fireml-dev/daisylib)

#### Versioning

Starting with Version 2.0.4-SNAPSHOT, versioning is handled like so:
- MAJOR - Changed when a massive part of the plugin is reworked
- MINOR - When a new minecraft update is released, also the time for breaking changes to be made.
- PATCH - When a new feature is added or a non-breaking change is made.

#### Maven

```xml
    <repository>
        <id>firedev-repo</id>
        <url>https://repo.firedev.uk/repository/maven-public/</url>
    </repository>
```
```xml
    <dependencies>
        <dependency>
            <groupId>uk.firedev</groupId>
            <artifactId>DaisyLib</artifactId>
            <version>2.2.0-SNAPSHOT</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

#### Gradle (Kotlin)
```kotlin
repositories {
    maven("https://repo.firedev.uk/repository/maven-public/")
}

dependencies {
    compileOnly("uk.firedev:DaisyLib:2.2.0-SNAPSHOT")
}
```