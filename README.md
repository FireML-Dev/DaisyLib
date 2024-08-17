## DaisyLib

A collection of classes for easier use of Paper's API.

https://ci.firedev.uk/job/DaisyLib/

[![CodeFactor](https://www.codefactor.io/repository/github/fireml-dev/daisylib/badge)](https://www.codefactor.io/repository/github/fireml-dev/daisylib)

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
            <version>2.0.3-SNAPSHOT</version>
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
    compileOnly("uk.firedev:DaisyLib:2.0.3-SNAPSHOT")
}
```