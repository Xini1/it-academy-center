package by.itacademy.center.repository;

import by.itacademy.center.shared.GitInTerminal;
import by.itacademy.center.shared.LoggingTerminal;
import by.itacademy.center.shared.RealTerminal;

import java.io.OutputStreamWriter;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (var writer = new OutputStreamWriter(System.out)) {
            new Repository(
                    Paths.get("./workflows").toAbsolutePath(),
                    new GitInTerminal(new LoggingTerminal(new RealTerminal(), writer)),
                    args[0]
            )
                    .configure();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
