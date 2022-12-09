package by.itacademy.center.shared;

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
        var repository = Paths.get("./cloned").toAbsolutePath();
        executeGitCommand(Paths.get("./").toAbsolutePath(), "clone", url, repository.toString());
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
        executeGitCommand(
                repository,
                "commit",
                "--author=\"Xini1 <tereshchenko.xini@gmail.com>\"",
                "-m",
                message
        );
    }

    @Override
    public void push(Path repository) {
        executeGitCommand(repository, "push");
    }

    @Override
    public boolean hasBranch(Path repository, String branch) {
        return executeGitCommand(
                repository,
                "for-each-ref",
                "--format=\"%(refname:short)\"",
                "refs/heads"
        )
                .contains(branch);
    }

    @Override
    public void createBranch(Path repository, String branch) {
        executeGitCommand(repository, "checkout", "-b");
    }

    private List<String> executeGitCommand(Path workingDirectory, String... arguments) {
        return terminal.execute(workingDirectory, "git", arguments);
    }
}
