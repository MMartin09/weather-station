import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("org.jetbrains.dokka") version "1.4.10.2"
}

group = "at.martinmoser"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

repositories {
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven(url="https://dl.bintray.com/kotlin/dokka")

    exclusiveContent {
        forRepository {
            maven {
                name = "JCenter"
                setUrl("https://jcenter.bintray.com/")
            }
        }
        filter {
            // Required for Dokka
            includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            includeGroup("org.jetbrains.dokka")
            includeModule("org.jetbrains", "markdown")
        }
    }
}

dependencies {
    compile("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("javax.usb:usb-api:1.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    compile("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
    compile("org.ini4j:ini4j:0.5.4")
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.4.10.2")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}