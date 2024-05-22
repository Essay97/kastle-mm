plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.sqldelight)
}

version = "1.0-SNAPSHOT"
group = "io.github.essay97"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.arrow)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqlite.driver)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("it.saggioland.kastle.db")
        }
    }
}
