package by.itacademy.center.shared;

import by.itacademy.center.shared.terminal.Terminal;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class FakeTerminal implements Terminal {

    private final List<Command> executedCommands = new ArrayList<>();

    @Override
    public List<String> execute(Path workingDirectory, String command, String... arguments) {
        var commands = new ArrayList<String>();
        commands.add(command);
        commands.addAll(List.of(arguments));
        executedCommands.add(new Command(workingDirectory, String.join(" ", commands)));
        return List.of();
    }

    public List<Command> executedCommands() {
        return List.copyOf(executedCommands);
    }

    public record Command(Path workingDirectory, String command) {}
}
