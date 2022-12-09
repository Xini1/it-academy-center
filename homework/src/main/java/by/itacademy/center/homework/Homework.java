package by.itacademy.center.homework;

import by.itacademy.center.shared.Directories;
import by.itacademy.center.shared.Git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class Homework {

    private final int number;
    private final Path homeworks;
    private final Git git;
    private final String repositoryUrl;

    Homework(int number, Path homeworks, Git git, String repositoryUrl) {
        this.number = number;
        this.homeworks = homeworks;
        this.git = git;
        this.repositoryUrl = repositoryUrl;
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
    }
}
