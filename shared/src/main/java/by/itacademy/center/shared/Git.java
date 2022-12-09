package by.itacademy.center.shared;

import java.nio.file.Path;

public interface Git {

    Path clone(String url);

    void add(Path repository, Path path);

    boolean hasNoFilesToCommit(Path repository);

    void config(Path repository, String name, String email);

    void commit(Path repository, String message);

    void push(Path repository);
}