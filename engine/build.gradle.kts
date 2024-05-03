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
    implementation(project(":core"))
    implementation(libs.clikt)
    implementation(libs.kotter)
    implementation(libs.arrow)
}

tasks.test {
    useJUnitPlatform()
}
