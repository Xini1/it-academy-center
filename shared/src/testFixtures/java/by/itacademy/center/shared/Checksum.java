package by.itacademy.center.shared;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Stream;

final class Checksum {

    private final byte[] digest;

    private Checksum(byte[] digest) {
        this.digest = digest;
    }

    static Checksum from(Path path) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            digest(path, messageDigest);
            return new Checksum(messageDigest.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new CouldNotCalculateChecksum(e);
        }
    }

    private static void digest(Path path, MessageDigest messageDigest) throws IOException {
        messageDigest.update(path.getFileName().toString().getBytes());
        if (Files.isDirectory(path)) {
            digestFromDirectory(path, messageDigest);
            return;
        }
        digestFromFile(path, messageDigest);
    }

    private static void digestFromFile(Path path, MessageDigest messageDigest) throws IOException {
        messageDigest.update(Files.readAllBytes(path));
    }

    private static void digestFromDirectory(Path path, MessageDigest messageDigest) throws IOException {
        try (Stream<Path> contents = Files.list(path)) {
            var iterator = contents.iterator();
            while (iterator.hasNext()) {
                digest(iterator.next(), messageDigest);
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digest);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        var checksum = (Checksum) object;
        return Arrays.equals(digest, checksum.digest);
    }

    static final class CouldNotCalculateChecksum extends RuntimeException {

        CouldNotCalculateChecksum(Throwable cause) {
            super(cause);
        }
    }
}
