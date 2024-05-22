plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "kastle-mm"
include("core")
include("dsl")
include("engine")
include("example-game")
