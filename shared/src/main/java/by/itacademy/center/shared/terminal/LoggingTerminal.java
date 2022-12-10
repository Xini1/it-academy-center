package by.itacademy.center.shared.terminal;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class LoggingTerminal implements Terminal {

    private final Terminal original;
    private final Writer writer;

    public LoggingTerminal(Terminal original, Writer writer) {
        this.original = original;
        this.writer = writer;
    }

    @Override
    public List<String> execute(Path workingDirectory, String command, String... arguments) {
        try {
            writeCommand(workingDirectory, command, arguments);
            var output = original.execute(workingDirectory, command, arguments);
            writeOutput(output);
            return output;
        } catch (IOException e) {
            throw new CouldNotWrite(e);
        }
    }

    private void writeOutput(List<String> output) throws IOException {
        for (String line : output) {
            writer.write(line);
            writer.write(System.lineSeparator());
        }
    }

    private void writeCommand(Path workingDirectory, String command, String[] arguments) throws IOException {
        var buffer = new ArrayList<String>();
        buffer.add(workingDirectory.toString());
        buffer.add(command);
        buffer.addAll(List.of(arguments));
        writer.write(String.join(" ", buffer));
        writer.write(System.lineSeparator());
    }

    private static final class CouldNotWrite extends RuntimeException {

        CouldNotWrite(Throwable cause) {
            super(cause);
        }
    }
}
