package by.itacademy.center.artifact;

import by.itacademy.center.shared.FakeGit;
import by.itacademy.center.shared.FakeTerminal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

final class ArtifactTest {

    @Test
    void givenRepositoryUrl_whenBuild_thenPathToJarPrinted(@TempDir Path path) throws IOException {
        var git = FakeGit.repository(path);
        var terminal = new FakeTerminal();
        var writer = new StringWriter();

        new Artifact(git, "url", terminal, Paths.get("src/test/resources"), writer).build();

        assertThat(path.relativize(Paths.get(writer.toString()))).isEqualTo(Paths.get("build/libs/artifact.jar"));
        assertThat(git.executedCommands()).containsExactly(new FakeGit.Clone("url"));
        assertThat(terminal.executedCommands())
                .containsExactly(new FakeTerminal.Command(path, "./gradlew :build"));
        assertThat(path).isDirectoryContaining("glob:**/file");
    }
}