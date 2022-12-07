plugins {
    application
}

application {
    mainClass.set("by.itacademy.center.repository.Main")
    tasks.run.get().workingDir = rootProject.projectDir
}

dependencies {
    testCompileOnly(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.assertj)
}

tasks.test {
    useJUnitPlatform()
}