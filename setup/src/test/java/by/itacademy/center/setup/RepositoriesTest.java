package by.itacademy.center.setup;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoriesTest {

    @Test
    void givenEmptyFile_whenWrite_thenArrayIsEmpty() throws IOException {
        var writer = new StringWriter();

        new Repositories(Paths.get("src/test/resources/empty"), writer).write();

        assertThat(writer).hasToString("repositories={\"repository\":[]}");
    }

    @Test
    void givenFileWithOneRepository_whenWrite_thenArrayHasOneElement() throws IOException {
        var writer = new StringWriter();

        new Repositories(Paths.get("src/test/resources/one-repository"), writer).write();

        assertThat(writer)
                .hasToString("""
                        repositories={"repository":["git@github.com:owner/repository.git"]}\
                        """);
    }

    @Test
    void givenFileWithTwoRepositories_whenWrite_thenArrayContainsAllElements() throws IOException {
        var writer = new StringWriter();

        new Repositories(Paths.get("src/test/resources/two-repositories"), writer).write();

        assertThat(writer)
                .hasToString("""
                        repositories={"repository":["git@github.com:owner/repository1.git",\
                        "git@github.com:owner/repository2.git"]}\
                        """);
    }
}