import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.9"
}

group = "at.martinmoser"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.17")
    implementation("javax.usb:usb-api:1.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    compile("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClassName = "MainKt"
}