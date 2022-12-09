package by.itacademy.center.setup;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

final class LessonsTest {

    @Test
    void givenFileIsEmpty_whenRead_thenCollectionIsEmpty() throws IOException {
        assertThat(new Lessons(Paths.get("src/main/resources/empty-lessons"), Clock.systemUTC()).read()).isEmpty();
    }

    @Test
    void givenCurrentDateIsEqualToLessonDate_whenRead_thenCollectionIsEmpty() throws IOException {
        assertThat(
                new Lessons(
                        Paths.get("src/main/resources/lessons"),
                        Clock.fixed(Instant.parse("2022-01-01T00:00:00Z"), ZoneOffset.UTC)
                )
                        .read()
        )
                .isEmpty();
    }

    @Test
    void givenCurrentDateIsAfterLessonDate_whenRead_thenCollectionHasLesson() throws IOException {
        assertThat(
                new Lessons(
                        Paths.get("src/main/resources/lessons"),
                        Clock.fixed(Instant.parse("2022-01-02T00:00:00Z"), ZoneOffset.UTC)
                )
                        .read()
        )
                .containsExactly(1);
    }
}