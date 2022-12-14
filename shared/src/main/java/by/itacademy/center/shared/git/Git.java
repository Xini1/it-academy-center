package by.itacademy.center.shared.git;

import java.nio.file.Path;

public interface Git {

    Path clone(String url);

    void add(Path repository, Path path);

    boolean hasNoFilesToCommit(Path repository);

    void commit(Path repository, String message);

    void push(Path repository);

    boolean hasRemoteBranch(Path repository, String branch);

    void createBranch(Path repository, String branch);

    String defaultBranch(Path repository);
}
