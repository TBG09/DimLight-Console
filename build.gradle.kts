plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.learn.java"
version = "1.0.0.0038"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.oshi:oshi-core:6.6.5")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "DimConsole.Core.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("all")
}
