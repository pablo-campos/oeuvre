package oeuvre;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Hello, World from Java!");
        System.out.println("========================================");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("JVM Architecture: " + System.getProperty("os.arch"));
        System.out.println("Timestamp: " + Instant.now());
        System.out.println("To add packages, add dependencies in java/build.gradle.kts");
        System.out.println("----------------------------------------\n");
    }
}
