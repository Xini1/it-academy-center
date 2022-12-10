package by.itacademy.center.repository;

import by.itacademy.center.shared.FakeGit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

final class RepositoryTest {

    @Test
    void givenWorkflowsDoNotExist_whenConfigure_thenWorkflowsPushed(@TempDir Path path) throws IOException {
        var git = FakeGit.repository(path);

        new Repository(Paths.get("src/test/resources"), git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new FakeGit.Clone("url"),
                        new FakeGit.Add(path, path.resolve(".github/workflows")),
                        new FakeGit.Diff(path),
                        new FakeGit.Commit(path, "GitHub workflows"),
                        new FakeGit.Push(path)
                );
    }

    @Test
    void givenWorkflowsAreTheSame_whenConfigure_thenNoWorkflowsPushed(@TempDir Path path) throws IOException {
        var workflows = path.resolve(".github/workflows");
        Files.createDirectories(workflows);
        var resources = Paths.get("src/test/resources");
        Files.copy(resources.resolve("workflow"), workflows.resolve("workflow"));
        var git = FakeGit.repository(path);

        new Repository(resources, git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new FakeGit.Clone("url"),
                        new FakeGit.Add(path, workflows),
                        new FakeGit.Diff(path)
                );
    }

    @Test
    void givenWorkflowsWereChanged_whenConfigure_thenWorkflowsPushed(@TempDir Path path) throws IOException {
        var workflows = path.resolve(".github/workflows");
        Files.createDirectories(workflows);
        Files.createFile(workflows.resolve("other-workflow"));
        var git = FakeGit.repository(path);

        new Repository(Paths.get("src/test/resources"), git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new FakeGit.Clone("url"),
                        new FakeGit.Add(path, workflows),
                        new FakeGit.Diff(path),
                        new FakeGit.Commit(path, "GitHub workflows"),
                        new FakeGit.Push(path)
                );
    }
}