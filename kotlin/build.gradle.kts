plugins {
    application
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("oeuvre.MainKt")
}

dependencies {
    // Add external packages/libraries here:
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    // implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
