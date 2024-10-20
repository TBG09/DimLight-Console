plugins {
    id("java")
}

group = "org.learn.java"
version = "1.0.0.0010"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "rsbdp.Core.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}