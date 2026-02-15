import org.gradle.kotlin.dsl.libs

plugins {
    id("java-library")
    id("com.diffplug.spotless") version "8.2.1"
}

group = "me.while1cry"
version = "0.4.0-SNAPSHOT"

description = "NapCat4J - High-level Java client framework for NapCatQQ"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
    withJavadocJar()
}

subprojects {

    apply(plugin = "java-library")
    apply(plugin = "com.diffplug.spotless")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    spotless {
        java {
            target("src/**/*.java")

            googleJavaFormat()
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks.test {
        useJUnitPlatform()
    }

}
