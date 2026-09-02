package oeuvre

import java.time.Instant

fun main() {
    println("========================================")
    println(" Hello, World from Kotlin!")
    println("========================================")
    println("Kotlin Version: ${KotlinVersion.CURRENT}")
    println("Java Runtime: ${System.getProperty("java.version")}")
    println("JVM Architecture: ${System.getProperty("os.arch")}")
    println("Timestamp: ${Instant.now()}")
    println("To add packages, add dependencies in kotlin/build.gradle.kts")
    println("----------------------------------------\n")
}
