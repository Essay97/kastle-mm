plugins {
    alias(libs.plugins.kotlin)
}

version = "1.0-SNAPSHOT"
group = "it.saggioland"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.arrow)
    implementation(libs.kotlinx.datetime)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
