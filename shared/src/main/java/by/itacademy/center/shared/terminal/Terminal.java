package by.itacademy.center.shared.terminal;

import java.nio.file.Path;
import java.util.List;

public interface Terminal {

    List<String> execute(Path workingDirectory, String command, String... arguments);
}
