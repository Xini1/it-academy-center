import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

val repositories: List<String> = Files.readAllLines(Paths.get("repositories"))
for (repository in repositories) {
    val process = ProcessBuilder("git", "clone", repository).start()
    process.waitFor(10, TimeUnit.SECONDS)
    if (process.exitValue() != 0) {
        println(process.inputStream.bufferedReader().readText())
    }
}