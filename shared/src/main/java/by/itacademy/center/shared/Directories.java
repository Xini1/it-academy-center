package by.itacademy.center.shared;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class Directories {

    private Directories() {
        throw new UnsupportedOperationException();
    }

    public static void copyContents(Path source, Path destination) throws IOException {
        try (Stream<Path> contents = Files.list(source)) {
            var iterator = contents.iterator();
            while (iterator.hasNext()) {
                var path = iterator.next();
                var target = destination.resolve(path.getFileName());
                if (Files.isDirectory(path)) {
                    copyContents(path, target);
                } else {
                    Files.copy(path, target);
                }
            }
        }
    }
}
