rootProject.name = "napcat4j"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    "napcat4j-core",
    "napcat4j-model",
    "napcat4j-cache",
    "napcat4j-framework"
)
