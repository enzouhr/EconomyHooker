plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.9"
    `maven-publish`
}

group = "me.itzflint.economyhooker"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    compileOnly(libs.annotations)

    compileOnly(libs.spigot)

    compileOnly(libs.bundles.economies)
    compileOnly(fileTree("libs"))
}

publishing {
    publications {
        create<MavenPublication>("Maven") {
            from(components["java"])
        }
    }
}

