plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)
}