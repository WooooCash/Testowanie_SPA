package ats.v1;

import ats.v1.pkb.Parser;
import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.proc_table.ProcTable;
import ats.v1.pkb.proc_table.ProcTableImpl;
import ats.v1.pkb.var_table.VarTable;
import ats.v1.pkb.var_table.VarTableImpl;
import ats.v1.spa_frontend.token.Token;
import ats.v1.spa_frontend.scanner.Scanner;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            processFile(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ready");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.length() == 0) break;
            System.out.println("FALSE " + input);
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
        // Parse
        VarTable varTable = new VarTableImpl();
        ProcTable procTable = new ProcTableImpl();
        Parser parser = new Parser(tokens, varTable, procTable);
        Ast ast = parser.parseTokens();
        System.out.println(ast.getRoot().toString());
        System.out.println(procTable);
        System.out.println(varTable);


    }
}