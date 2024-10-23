plugins {
    id("java")
}

group = "org.learn.java"
version = "1.0.0.0034"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.java.dev.jna:jna:5.15.0")
    implementation("net.java.dev.jna:jna-platform:5.15.0")


}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "DimConsole.Core.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}