package by.itacademy.center.homework;

import by.itacademy.center.shared.Directories;
import by.itacademy.center.shared.git.Git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class Homework {

    private final int number;
    private final Path homeworks;
    private final Git git;
    private final String repositoryUrl;
    private final String personalAccessToken;
    private final GitHub gitHub;

    Homework(int number, Path homeworks, Git git, String repositoryUrl, String personalAccessToken, GitHub gitHub) {
        this.number = number;
        this.homeworks = homeworks;
        this.git = git;
        this.repositoryUrl = repositoryUrl;
        this.personalAccessToken = personalAccessToken;
        this.gitHub = gitHub;
    }

    void publish() throws IOException {
        var repository = git.clone(repositoryUrl);
        var branch = "homework/lesson" + number;
        if (git.hasBranch(repository, branch)) {
            return;
        }
        git.createBranch(repository, branch);
        var targetHomeworkDirectory = Paths.get("src/main/java/by/itacademy/lesson" + number);
        var homeworkDirectoryInRepository = repository.resolve(targetHomeworkDirectory);
        var sourceHomeworkDirectory = homeworks.resolve(targetHomeworkDirectory);
        Files.createDirectories(homeworkDirectoryInRepository);
        Directories.copyContents(sourceHomeworkDirectory, homeworkDirectoryInRepository);
        git.add(repository, homeworkDirectoryInRepository);
        git.commit(repository, branch);
        git.push(repository);
        gitHub.openPullRequest(
                personalAccessToken,
                repositoryOwner(),
                repositoryName(),
                new GitHub.Payload(branch, "", branch, git.defaultBranch(repository))
        );
    }

    private String repositoryOwner() {
        return repositoryUrl.substring("git@github.com:".length(), repositoryUrl.indexOf('/'));
    }

    private String repositoryName() {
        return repositoryUrl.substring(repositoryUrl.indexOf('/') + 1, repositoryUrl.indexOf(".git"));
    }
}
