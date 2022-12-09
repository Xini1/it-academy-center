package by.itacademy.center.shared;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GitFake implements Git {

    private final Collection<Command> executedCommands = new ArrayList<>();
    private final Path repository;
    private final Checksum checksum;

    private GitFake(Path repository, Checksum checksum) {
        this.repository = repository;
        this.checksum = checksum;
    }

    public static GitFake repository(Path repository) throws NoSuchAlgorithmException, IOException {
        return new GitFake(repository, Checksum.from(repository));
    }

    @Override
    public Path clone(String url) {
        executedCommands.add(new Clone(url));
        return repository;
    }

    @Override
    public void add(Path repository, Path path) {
        executedCommands.add(new Add(repository, path));
    }

    @Override
    public boolean hasNoFilesToCommit(Path repository) {
        executedCommands.add(new Diff(repository));
        try {
            return Checksum.from(repository).equals(checksum);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void config(Path repository, String name, String email) {
        executedCommands.add(new ConfigUserName(repository, name));
        executedCommands.add(new ConfigUserEmail(repository, email));
    }

    @Override
    public void commit(Path repository, String message) {
        executedCommands.add(new Commit(repository, message));
    }

    @Override
    public void push(Path repository) {
        executedCommands.add(new Push(repository));
    }

    public Collection<Command> executedCommands() {
        return List.copyOf(executedCommands);
    }

    public interface Command {}

    public record Clone(String url) implements Command {}

    public record Add(Path repository, Path path) implements Command {}

    public record Diff(Path repository) implements Command {}

    public record ConfigUserName(Path repository, String name) implements Command {}

    public record ConfigUserEmail(Path repository, String email) implements Command {}

    public record Commit(Path repository, String message) implements Command {}

    public record Push(Path repository) implements Command {}
}
