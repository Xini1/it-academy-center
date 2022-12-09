package by.itacademy.center.repository;

import by.itacademy.center.shared.Git;

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
        copyContents(workflows, workflowsInRepository);
        git.add(repository, workflowsInRepository);
        if (git.hasNoFilesToCommit(repository)) {
            return;
        }
        git.config(repository, "Xini1", "tereshchenko.xini@gmail.com");
        git.commit(repository, "GitHub workflows");
        git.push(repository);
    }

    private void copyContents(Path source, Path destination) throws IOException {
        try (Stream<Path> contents = Files.list(source)) {
            var iterator = contents.iterator();
            while (iterator.hasNext()) {
                var path = iterator.next();
                Files.copy(path, destination.resolve(path.getFileName()));
            }
        }
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
