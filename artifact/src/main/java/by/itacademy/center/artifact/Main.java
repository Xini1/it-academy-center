package by.itacademy.center.artifact;

import by.itacademy.center.shared.TeeWriter;
import by.itacademy.center.shared.git.GitInTerminal;
import by.itacademy.center.shared.terminal.LoggingTerminal;
import by.itacademy.center.shared.terminal.RealTerminal;

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (var writer = new TeeWriter(new FileWriter(args[0]), new OutputStreamWriter(System.out))) {
            var terminal = new LoggingTerminal(new RealTerminal(), writer);
            new OutputFormatter(writer)
                    .write(
                            new Artifact(
                                    new GitInTerminal(terminal),
                                    args[1],
                                    terminal,
                                    Paths.get("artifact/src/main/resources").toAbsolutePath()
                            )
                                    .build()
                    );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
