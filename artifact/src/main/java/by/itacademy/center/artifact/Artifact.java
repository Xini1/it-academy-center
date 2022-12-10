package by.itacademy.center.artifact;

import by.itacademy.center.shared.Directories;
import by.itacademy.center.shared.git.Git;
import by.itacademy.center.shared.terminal.Terminal;

import java.io.IOException;
import java.nio.file.Path;

final class Artifact {

    private final Git git;
    private final String repositoryUrl;
    private final Terminal terminal;
    private final Path gradleFiles;

    Artifact(Git git, String repositoryUrl, Terminal terminal, Path gradleFiles) {
        this.git = git;
        this.repositoryUrl = repositoryUrl;
        this.terminal = terminal;
        this.gradleFiles = gradleFiles;
    }

    Path build() throws IOException {
        var repository = git.clone(repositoryUrl);
        Directories.copyContents(gradleFiles, repository);
        terminal.execute(repository, "./gradlew", "build");
        return repository.resolve("build/libs/artifact.jar");
    }
}
