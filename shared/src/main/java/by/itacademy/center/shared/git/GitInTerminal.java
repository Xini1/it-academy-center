package by.itacademy.center.shared.git;

import by.itacademy.center.shared.terminal.Terminal;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class GitInTerminal implements Git {

    private final Terminal terminal;

    public GitInTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public Path clone(String url) {
        var repository = Paths.get("cloned").toAbsolutePath();
        executeGitCommand(Paths.get("").toAbsolutePath(), "clone", url, repository.toString());
        return repository;
    }

    @Override
    public void add(Path repository, Path path) {
        executeGitCommand(repository, "add", path.toString());
    }

    @Override
    public boolean hasNoFilesToCommit(Path repository) {
        return executeGitCommand(repository, "diff", "--name-only", "--cached").isEmpty();
    }

    @Override
    public void commit(Path repository, String message) {
        executeGitCommand(repository, "config", "user.name", "Xini1");
        executeGitCommand(repository, "config", "user.email", "tereshchenko.xini@gmail.com");
        executeGitCommand(repository, "commit", "-m", message);
    }

    @Override
    public void push(Path repository) {
        executeGitCommand(repository, "push", "-u", "origin", activeBranch(repository));
    }

    @Override
    public boolean hasBranch(Path repository, String branch) {
        return executeGitCommand(
                repository,
                "for-each-ref",
                "--format=\"%(refname:short)\"",
                "refs/heads"
        )
                .contains(branch); //TODO
    }

    @Override
    public void createBranch(Path repository, String branch) {
        executeGitCommand(repository, "checkout", "-b", branch);
    }

    @Override
    public String defaultBranch(Path repository) {
        var remoteBranch = executeGitCommand(repository, "rev-parse", "--abbrev-ref", "origin/HEAD").get(0);
        return remoteBranch.substring(remoteBranch.indexOf('/') + 1);
    }

    private String activeBranch(Path repository) {
        return executeGitCommand(repository, "rev-parse", "--abbrev-ref", "HEAD").get(0);
    }

    private List<String> executeGitCommand(Path workingDirectory, String... arguments) {
        return terminal.execute(workingDirectory, "git", arguments);
    }
}
