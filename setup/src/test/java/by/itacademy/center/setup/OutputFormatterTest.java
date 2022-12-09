package by.itacademy.center.setup;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class OutputFormatterTest {

    @Test
    void givenSomeRepositoriesAndLessons_whenWrite_thenFormatIsAsExpected() throws IOException {
        var writer = new StringWriter();

        new OutputFormatter(writer).write(List.of(), List.of());

        assertThat(writer)
                .hasToString("""
                        setup={"repository":[],"lesson":[]}\
                        """);
    }
}