package by.itacademy.center.repository;

import by.itacademy.center.shared.Directories;
import by.itacademy.center.shared.git.Git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

final class Repository {

    private final Path workflows;
    private final Git git;
    private final String repositoryUrl;

    Repository(Path workflows, Git git, String repositoryUrl) {
        this.workflows = workflows;
        this.git = git;
        this.repositoryUrl = repositoryUrl;
    }

    void configure() throws IOException {
        var repository = git.clone(repositoryUrl);
        var workflowsInRepository = repository.resolve(".github").resolve("workflows");
        delete(workflowsInRepository);
        Files.createDirectories(workflowsInRepository);
        Directories.copyContents(workflows, workflowsInRepository);
        git.add(repository, workflowsInRepository);
        if (git.hasNoFilesToCommit(repository)) {
            return;
        }
        git.commit(repository, "GitHub workflows");
        git.push(repository);
    }

    private void delete(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> contents = Files.list(path)) {
                var iterator = contents.iterator();
                while (iterator.hasNext()) {
                    delete(iterator.next());
                }
            }
        }
        Files.deleteIfExists(path);
    }
}
