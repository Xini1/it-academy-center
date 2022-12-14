package by.itacademy.center.homework;

import by.itacademy.center.shared.git.GitInTerminal;
import by.itacademy.center.shared.terminal.LoggingTerminal;
import by.itacademy.center.shared.terminal.RealTerminal;

import java.io.OutputStreamWriter;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (var writer = new OutputStreamWriter(System.out)) {
            new Homework(
                    Integer.parseInt(args[1]),
                    Paths.get("homeworks").toAbsolutePath(),
                    new GitInTerminal(new LoggingTerminal(new RealTerminal(), writer)),
                    args[0],
                    args[2],
                    new RestApiGitHub()
            )
                    .publish();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
