package by.itacademy.center.setup;

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;

final class Main {

    public static void main(String[] args) {
        try (Writer writer = new TeeWriter(new FileWriter(args[0]), new OutputStreamWriter(System.out))) {
            new OutputFormatter(writer)
                    .write(
                            Files.readAllLines(Paths.get("repositories").toAbsolutePath()),
                            new Lessons(Paths.get("lessons").toAbsolutePath(), Clock.systemUTC()).read()
                    );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
