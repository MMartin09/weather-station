pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven(url = "https://dl.bintray.com/kotlin/dokka")
    }
}
rootProject.name = "weather-station"
