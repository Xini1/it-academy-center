package by.itacademy.center.homework;

import by.itacademy.center.shared.Directories;
import by.itacademy.center.shared.git.Git;

import java.io.IOException;
import java.nio.file.Path;

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
        if (git.hasRemoteBranch(repository, branch)) {
            return;
        }
        git.createBranch(repository, branch);
        var sourceHomeworkDirectory = homeworks.resolve(String.valueOf(number));
        Directories.copyContents(sourceHomeworkDirectory, repository);
        git.add(repository, repository);
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
