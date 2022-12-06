#!/usr/bin/env kotlin

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun execute(workingDirectory: Path, vararg commands: String): String {
    val process = ProcessBuilder(*commands).directory(workingDirectory.toFile()).start()
    process.waitFor(10, TimeUnit.SECONDS)
    if (process.exitValue() != 0) {
        System.err.println(process.errorStream.bufferedReader().readText())
        exitProcess(process.exitValue())
    }
    return process.inputStream.bufferedReader().readText()
}

fun execute(vararg commands: String) = execute(Paths.get("./"), *commands)

fun clear(directory: Path) {
    if (!Files.exists(directory)) {
        return
    }
    Files.list(directory).forEach(Files::deleteIfExists)
}

fun copyFiles(sourceDirectory: Path, targetDirectory: Path) {
    if (!Files.exists(targetDirectory)) {
        Files.createDirectory(targetDirectory)
    }
    Files.list(sourceDirectory).forEach { Files.copy(it, targetDirectory.resolve(it.fileName)) }
}

fun hasFilesToCommit(directory: Path) =
    execute(directory, "git", "diff", "--name-only", "--cached").isNotEmpty()

val clonedRepositoryDirectory: Path = Paths.get("repository")
val workflowsDirectoryInRepository: Path = clonedRepositoryDirectory.resolve(".github").resolve("workflows")
val workflowsDirectory: Path = Paths.get("workflows")

execute("git", "clone", args[0], clonedRepositoryDirectory.toString())
clear(workflowsDirectoryInRepository)
copyFiles(workflowsDirectory, workflowsDirectoryInRepository)
execute(
    clonedRepositoryDirectory,
    "git",
    "add",
    clonedRepositoryDirectory.relativize(workflowsDirectoryInRepository).toString()
)
println("has-files-to-commit=${hasFilesToCommit(clonedRepositoryDirectory)}")