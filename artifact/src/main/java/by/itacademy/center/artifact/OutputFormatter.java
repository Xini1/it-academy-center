package by.itacademy.center.artifact;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

final class OutputFormatter {

    private final Writer writer;

    OutputFormatter(Writer writer) {
        this.writer = writer;
    }

    void write(Path artifact) throws IOException {
        writer.write("artifact=" + Paths.get("").toAbsolutePath().relativize(artifact));
    }
}
