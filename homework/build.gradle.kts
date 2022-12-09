plugins {
    application
}

application {
    mainClass.set("by.itacademy.center.homework.Main")
    tasks.run.get().workingDir = rootProject.projectDir
}

dependencies {
    implementation(project(":shared"))

    testImplementation(testFixtures(project(":shared")))
    testCompileOnly(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.assertj)
}

tasks.test {
    useJUnitPlatform()
}