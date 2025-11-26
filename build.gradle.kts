plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
}

group = "dev.httpmarco.polocloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "polocloud-snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.13.2")
    compileOnly("dev.httpmarco.polocloud:polocloud-proto:3.0.0-pre.7.3-SNAPSHOT")
}
