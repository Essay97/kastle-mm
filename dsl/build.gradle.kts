plugins {
    alias(libs.plugins.kotlin)
    `java-library`
}

version = "1.0-SNAPSHOT"
group = "it.saggioland"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.datetime)
    api(project(":core"))
}

tasks.test {
    useJUnitPlatform()
}
