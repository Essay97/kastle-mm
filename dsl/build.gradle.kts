plugins {
    alias(libs.plugins.kotlin)
}

version = "1.0-SNAPSHOT"
group = "it.saggioland"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.datetime)
    implementation(project(":core"))
}

tasks.test {
    useJUnitPlatform()
}
