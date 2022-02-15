import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    application
}

group = "me.georgejpadayatti"
version = "1.0-SNAPSHOT"

repositories {
    maven { url= uri("https://repo.sovrin.org/repository/maven-public") }
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.jai-imageio:jai-imageio-jpeg2000:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("org.hyperledger:indy:1.15.0")
    implementation("com.google.code.gson:gson:2.9.0")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}