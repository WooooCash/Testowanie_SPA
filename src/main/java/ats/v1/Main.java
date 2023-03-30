package ats.v1;

import ats.v1.spa_frontend.Token;
import ats.v1.spa_frontend.scanner.Scanner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            processFile("sample_program.smpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        process(new String(bytes, Charset.defaultCharset()));
    }

    private static void process(String programSource) {
        // Scan
        Scanner scanner = new Scanner(programSource);
        List<Token> tokens = scanner.scanTokens();
        System.out.printf("%2s %11s %9s %2s\n", "LN", "TYPE", "TEXT", "VAL");
        for (Token t : tokens) System.out.println(t);
        // Parse
    }
}