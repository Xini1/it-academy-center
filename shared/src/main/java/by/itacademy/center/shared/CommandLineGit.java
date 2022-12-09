package by.itacademy.center.shared;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class CommandLineGit implements Git {

    private final Writer writer;

    public CommandLineGit(Writer writer) {
        this.writer = writer;
    }

    @Override
    public Path clone(String url) {
        var repository = Paths.get("cloned").toAbsolutePath();
        executeGitCommand(Paths.get("./"), "clone", url, repository.toString());
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

    private List<String> executeGitCommand(Path workingDirectory, String command, String... arguments) {
        try {
            var commands = commands(command, arguments);
            write(commands.toString());
            var process = process(workingDirectory, commands);
            var output = readOutput(process);
            write(output.toString());
            process.waitFor();
            return output;
        } catch (IOException e) {
            throw new ExecutionFailed(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExecutionFailed(e);
        }
    }

    private void write(String message) throws IOException {
        if (message.isEmpty()) {
            return;
        }
        writer.write(message);
        writer.write(System.lineSeparator());
    }

    private List<String> readOutput(Process process) throws IOException {
        try (var reader = process.inputReader()) {
            return reader.lines()
                    .toList();
        }
    }

    private Process process(Path workingDirectory, List<String> commands) throws IOException {
        return new ProcessBuilder()
                .directory(workingDirectory.toFile())
                .command(commands)
                .redirectErrorStream(true)
                .start();
    }

    private List<String> commands(String command, String[] arguments) {
        var commands = new ArrayList<String>();
        commands.add("git");
        commands.add(command);
        commands.addAll(List.of(arguments));
        return commands;
    }

    static final class ExecutionFailed extends RuntimeException {

        private ExecutionFailed(Throwable cause) {
            super(cause);
        }
    }
}
