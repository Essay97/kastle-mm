plugins {
    alias(libs.plugins.kotlin)
    application
}

version = "1.0-SNAPSHOT"
group = "io.github.essay97"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":core"))
    implementation(project(":dsl"))
    implementation(libs.clikt)
    implementation(libs.kotter)
    implementation(libs.arrow)
    implementation(libs.kotlinx.datetime)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("it.saggioland.kastle.MainKt")
}
