plugins {
    alias(libs.plugins.kotlin)
    application
    alias(libs.plugins.sqldelight)
}

version = "1.0-SNAPSHOT"
group = "it.saggioland"

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
    implementation(libs.sqlite.driver)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("it.saggioland.kastle.MainKt")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("it.saggioland.kastle.db")
        }
    }
}
