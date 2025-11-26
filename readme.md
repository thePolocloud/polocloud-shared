```kotlin
repositories {
    maven {
        name = "polocloud-snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

dependencies {
    implementation("dev.httpmarco.polocloud:shared:3.0.0-pre.8-SNAPSHOT")
}
```