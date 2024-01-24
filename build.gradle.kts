import io.qameta.allure.gradle.base.AllureExtension

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.qameta.allure") version "2.11.2"
    id("com.github.ben-manes.versions") version "0.11.1"
}

group = "com.autodoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.16.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.6.2")
    testImplementation("io.qameta.allure:allure-junit5:2.25.0")
    testImplementation("com.luissoares:selenium-testing-library:3.6")
}

tasks.test {
    useJUnitPlatform()
}

configure<AllureExtension> {
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
    }
}

kotlin {
    jvmToolchain(11)
}