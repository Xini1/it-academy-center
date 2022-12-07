package by.itacademy.center.setup;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

final class Repositories {

    private final Path source;
    private final Writer writer;
    private final Gson gson = new Gson();

    Repositories(Path source, Writer writer) {
        this.source = source;
        this.writer = writer;
    }

    void write() throws IOException {
        writer.write("repositories=");
        writer.write(gson.toJson(new Json(Files.readAllLines(source))));
    }

    private record Json(Collection<String> repository) {}
}
