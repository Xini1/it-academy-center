plugins {
    application
}

application {
    mainClass.set("by.itacademy.center.review.Main")
    tasks.run.get().workingDir = rootProject.projectDir
}

repositories {
    flatDir {
        dirs(rootProject.projectDir.resolve(properties["artifact"].toString()))
    }
}

dependencies {
    runtimeOnly(files(rootProject.projectDir.resolve(properties["artifact"].toString())))
}