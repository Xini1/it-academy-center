package by.itacademy.center.homework;

import by.itacademy.center.shared.FakeGit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class HomeworkTest {

    @Test
    void givenBranchWithHomeworkExists_whenPublish_thenNoHomeworkIsPublished(@TempDir Path path) throws IOException {
        var git = FakeGit.repository(path, List.of("main", "homework/lesson1"));
        var gitHub = new FakeGitHub();

        new Homework(
                1,
                Paths.get("src/test/resources"),
                git,
                "git@github.com:owner/repository.git",
                "token",
                gitHub
        )
                .publish();

        assertThat(git.executedCommands())
                .containsExactly(
                        new FakeGit.Clone("git@github.com:owner/repository.git"),
                        new FakeGit.Branches(path)
                );
        assertThat(gitHub.pullRequest()).isNull();
    }

    @Test
    void givenBranchWithHomeworkDoNotExist_whenPublish_thenHomeworkIsPublished(@TempDir Path path) throws IOException {
        var git = FakeGit.repository(path);
        var gitHub = new FakeGitHub();

        new Homework(
                1,
                Paths.get("src/test/resources"),
                git,
                "git@github.com:owner/repository.git",
                "token",
                gitHub
        )
                .publish();

        assertThat(git.executedCommands())
                .containsExactly(
                        new FakeGit.Clone("git@github.com:owner/repository.git"),
                        new FakeGit.Branches(path),
                        new FakeGit.Checkout(path, "homework/lesson1"),
                        new FakeGit.Add(path, path.resolve("src/main/java/by/itacademy/lesson1")),
                        new FakeGit.Commit(path, "homework/lesson1"),
                        new FakeGit.Push(path)
                );
        assertThat(gitHub.pullRequest())
                .isEqualTo(
                        new FakeGitHub.PullRequest(
                                "token",
                                "owner",
                                "repository",
                                new GitHub.Payload(
                                        "homework/lesson1",
                                        "",
                                        "homework/lesson1",
                                        "main"
                                )
                        )
                );
    }
}