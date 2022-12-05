import java.nio.file.Files
import java.nio.file.Paths

fun repositories(): Collection<String> = Files.readAllLines(Paths.get("repositories"))

fun withDoubleQuotes(strings: Collection<String>) =
    strings.asSequence()
        .map { '"' + it + '"' }
        .toList()

fun json(repositories: Collection<String>) = """{"include":$repositories}"""

println(json(withDoubleQuotes(repositories())))