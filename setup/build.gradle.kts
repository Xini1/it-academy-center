plugins {
    application
}

application {
    mainClass.set("by.itacademy.center.setup.Main")
    tasks.run.get().workingDir = rootProject.projectDir
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.gson)

    testCompileOnly(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.assertj)
}

tasks.test {
    useJUnitPlatform()
}