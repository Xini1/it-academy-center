package by.itacademy.center.shared.terminal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class RealTerminal implements Terminal {

    @Override
    public List<String> execute(Path workingDirectory, String command, String... arguments) {
        try {
            var process = process(workingDirectory, commands(command, arguments));
            var output = readOutput(process);
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new ExecutionFailed(process.exitValue());
            }
            return output;
        } catch (IOException e) {
            throw new ExecutionFailed(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExecutionFailed(e);
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
        commands.add(command);
        commands.addAll(List.of(arguments));
        return commands;
    }

    private List<String> readOutput(Process process) throws IOException {
        try (var reader = process.inputReader()) {
            return reader.lines()
                    .toList();
        }
    }

    private static final class ExecutionFailed extends RuntimeException {

        ExecutionFailed(Throwable cause) {
            super(cause);
        }

        ExecutionFailed(int exitCode) {
            super("Exit code is " + exitCode);
        }
    }
}
