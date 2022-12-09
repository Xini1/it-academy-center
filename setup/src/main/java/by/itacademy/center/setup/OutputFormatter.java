package by.itacademy.center.setup;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

final class OutputFormatter {

    private final Writer writer;
    private final Gson gson = new Gson();

    OutputFormatter(Writer writer) {
        this.writer = writer;
    }

    void write(Collection<String> repositories, Collection<Integer> lessons) throws IOException {
        writer.write("setup=");
        writer.write(gson.toJson(new Json(repositories, lessons)));
    }

    private record Json(Collection<String> repository, Collection<Integer> lesson) {}
}
