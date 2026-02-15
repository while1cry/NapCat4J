import org.gradle.kotlin.dsl.libs

dependencies {
    api(libs.annotations)
    implementation(libs.jackson.databind)

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.logback.classic)
}
