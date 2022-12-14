package by.itacademy.center.shared;

import by.itacademy.center.shared.git.Git;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FakeGit implements Git {

    private final List<Command> executedCommands = new ArrayList<>();
    private final Path repository;
    private final Collection<String> branches;
    private final Checksum checksum;

    private FakeGit(Path repository, Collection<String> branches, Checksum checksum) {
        this.repository = repository;
        this.branches = new ArrayList<>(branches);
        this.checksum = checksum;
    }

    public static FakeGit repository(Path repository) {
        return new FakeGit(repository, List.of("main"), Checksum.from(repository));
    }

    public static FakeGit repository(Path repository, Collection<String> branches) {
        return new FakeGit(repository, branches, Checksum.from(repository));
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
        return Checksum.from(repository).equals(checksum);
    }

    @Override
    public void commit(Path repository, String message) {
        executedCommands.add(new Commit(repository, message));
    }

    @Override
    public void push(Path repository) {
        executedCommands.add(new Push(repository));
    }

    @Override
    public boolean hasRemoteBranch(Path repository, String branch) {
        executedCommands.add(new Branches(repository));
        return branches.contains(branch);
    }

    @Override
    public void createBranch(Path repository, String branch) {
        executedCommands.add(new Checkout(repository, branch));
    }

    @Override
    public String defaultBranch(Path repository) {
        return branches.iterator().next();
    }

    public List<Command> executedCommands() {
        return List.copyOf(executedCommands);
    }

    public interface Command {}

    public record Clone(String url) implements Command {}

    public record Add(Path repository, Path path) implements Command {}

    public record Diff(Path repository) implements Command {}

    public record Branches(Path repository) implements Command {}

    public record Checkout(Path repository, String branch) implements Command {}

    public record Commit(Path repository, String message) implements Command {}

    public record Push(Path repository) implements Command {}
}
