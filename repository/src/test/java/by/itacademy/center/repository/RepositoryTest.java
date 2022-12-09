package by.itacademy.center.repository;

import by.itacademy.center.shared.GitFake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryTest {

    @Test
    void givenWorkflowsDoNotExist_whenConfigure_thenWorkflowsPushed(@TempDir Path path) throws Exception {
        var git = GitFake.repository(path);

        new Repository(Paths.get("src/test/resources"), git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new GitFake.Clone("url"),
                        new GitFake.Add(path, path.resolve(".github").resolve("workflows")),
                        new GitFake.Diff(path),
                        new GitFake.ConfigUserName(path, "Xini1"),
                        new GitFake.ConfigUserEmail(path, "tereshchenko.xini@gmail.com"),
                        new GitFake.Commit(path, "GitHub workflows"),
                        new GitFake.Push(path)
                );
    }

    @Test
    void givenWorkflowsAreTheSame_whenConfigure_thenNoWorkflowsPushed(@TempDir Path path) throws Exception {
        var workflows = path.resolve(".github/workflows");
        Files.createDirectories(workflows);
        var resources = Paths.get("src/test/resources");
        Files.copy(resources.resolve("workflow"), workflows.resolve("workflow"));
        var git = GitFake.repository(path);

        new Repository(resources, git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new GitFake.Clone("url"),
                        new GitFake.Add(path, workflows),
                        new GitFake.Diff(path)
                );
    }

    @Test
    void givenWorkflowsWereChanged_whenConfigure_thenWorkflowsPushed(@TempDir Path path) throws Exception {
        var workflows = path.resolve(".github/workflows");
        Files.createDirectories(workflows);
        Files.createFile(workflows.resolve("other-workflow"));
        var git = GitFake.repository(path);

        new Repository(Paths.get("src/test/resources"), git, "url").configure();

        assertThat(git.executedCommands())
                .containsExactly(
                        new GitFake.Clone("url"),
                        new GitFake.Add(path, workflows),
                        new GitFake.Diff(path),
                        new GitFake.ConfigUserName(path, "Xini1"),
                        new GitFake.ConfigUserEmail(path, "tereshchenko.xini@gmail.com"),
                        new GitFake.Commit(path, "GitHub workflows"),
                        new GitFake.Push(path)
                );
    }
}