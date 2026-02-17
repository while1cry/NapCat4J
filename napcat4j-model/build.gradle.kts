import org.gradle.kotlin.dsl.libs

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    api(libs.annotations)
    implementation(libs.jackson.databind)

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.logback.classic)
}
