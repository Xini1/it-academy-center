package by.itacademy.center.repository;

import by.itacademy.center.shared.CommandLineGit;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) {
        try (var writer = new OutputStreamWriter(System.out)) {
            new Repository(Paths.get("workflows"), new CommandLineGit(writer), args[0]).configure();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
