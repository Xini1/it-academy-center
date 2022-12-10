package by.itacademy.center.artifact;

import by.itacademy.center.shared.git.GitInTerminal;
import by.itacademy.center.shared.terminal.LoggingTerminal;
import by.itacademy.center.shared.terminal.RealTerminal;

import java.io.OutputStreamWriter;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (var writer = new OutputStreamWriter(System.out)) {
            var terminal = new LoggingTerminal(new RealTerminal(), writer);
            new Artifact(
                    new GitInTerminal(terminal),
                    args[0],
                    terminal,
                    Paths.get("artifact/src/main/resources").toAbsolutePath(),
                    writer
            )
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
