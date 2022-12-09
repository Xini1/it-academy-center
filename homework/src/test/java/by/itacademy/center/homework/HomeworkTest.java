package by.itacademy.center.homework;

import by.itacademy.center.shared.GitFake;
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
        var git = GitFake.repository(path, List.of("main", "homework/lesson1"));

        new Homework(1, Paths.get("src/test/resources"), git, "url").publish();

        assertThat(git.executedCommands()).containsExactly(new GitFake.Clone("url"), new GitFake.Branches(path));
    }

    @Test
    void givenBranchWithHomeworkDoNotExist_whenPublish_thenHomeworkIsPublished(@TempDir Path path) throws IOException {
        var git = GitFake.repository(path);

        new Homework(1, Paths.get("src/test/resources"), git, "url").publish();

        assertThat(git.executedCommands())
                .containsExactly(
                        new GitFake.Clone("url"),
                        new GitFake.Branches(path),
                        new GitFake.Checkout(path, "homework/lesson1"),
                        new GitFake.Add(path, path.resolve("src/main/java/by/itacademy/lesson1")),
                        new GitFake.Commit(path, "homework/lesson1"),
                        new GitFake.Push(path)
                );
    }
}