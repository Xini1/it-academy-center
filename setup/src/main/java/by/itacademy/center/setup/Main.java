package by.itacademy.center.setup;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (Writer writer = new TeeWriter(new FileWriter(args[0]), new OutputStreamWriter(System.out))) {
            new Repositories(Paths.get("repositories"), writer).write();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
