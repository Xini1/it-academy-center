package by.itacademy.center.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;

final class Lessons {

    private final Path source;
    private final Clock clock;

    Lessons(Path source, Clock clock) {
        this.source = source;
        this.clock = clock;
    }

    Collection<Integer> read() throws IOException {
        var now = LocalDate.now(clock);
        try (var lines = Files.lines(source)) {
            return lines.map(Lesson::new)
                    .filter(lesson -> lesson.isBefore(now))
                    .map(Lesson::number)
                    .toList();
        }
    }

    private record Lesson(String line) {

        boolean isBefore(LocalDate date) {
            return LocalDate.parse(line.split("=")[1]).isBefore(date);
        }

        int number() {
            return Integer.parseInt(line.split("=")[0]);
        }
    }
}
