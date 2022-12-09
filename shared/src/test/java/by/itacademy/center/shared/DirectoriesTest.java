package by.itacademy.center.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

final class DirectoriesTest {

    @Test
    void givenNestedDirectories_whenCopyContents_thenAllNestedDirectoriesAreCopied(@TempDir Path path)
            throws IOException {
        var source = Paths.get("src/test/resources");

        Directories.copyContents(source, path);

        assertThat(path.resolve("directory/file-in-directory"))
                .hasSameBinaryContentAs(source.resolve("directory/file-in-directory"));
        assertThat(path.resolve("file")).hasSameBinaryContentAs(source.resolve("file"));
    }
}