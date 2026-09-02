plugins {
    application
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("oeuvre.Main")
}

dependencies {
    // Add external packages/libraries here:
    // implementation("com.google.guava:guava:33.4.0-jre")
    // implementation("com.google.code.gson:gson:2.11.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

tasks.test {
    useJUnitPlatform()
}
