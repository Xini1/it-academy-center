package by.itacademy.center.artifact;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

final class OutputFormatterTest {

    @Test
    void givenArtifactPath_whenWrite_thenRelativePathPrinted() throws IOException {
        var writer = new StringWriter();

        new OutputFormatter(writer).write(Paths.get("build/libs/artifact.jar").toAbsolutePath());

        assertThat(writer).hasToString("artifact=build/libs/artifact.jar");
    }
}