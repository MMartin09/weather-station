import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
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
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClassName = "MainKt"
}