package ats.v1;

import ats.v1.pkb.Parser;
import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.proc_table.ProcTable;
import ats.v1.pkb.proc_table.ProcTableImpl;
import ats.v1.pkb.var_table.VarTable;
import ats.v1.pkb.var_table.VarTableImpl;
import ats.v1.query.parser.QueryParser;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenTableWriter;
import ats.v1.spa_frontend.token.Token;
import ats.v1.spa_frontend.scanner.Scanner;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            processFile(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ready");
        //TODO Print only answer after Ready (one line) - otherwise PipeTester don't know what is going on
        java.util.Scanner sc = new java.util.Scanner(System.in);
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = null;
        QueryTokenTableWriter queryTokenTableWriter = new QueryTokenTableWriter();
        while (sc.hasNextLine()) {
            //Waiting for input in first and second line
            String firstLine = sc.nextLine();
            String secondLine = sc.nextLine();
            if ((firstLine + secondLine).length() == 0) break;
            //Creating tokens
            tokens = reader.parse(firstLine + secondLine);
            //Printing out tokens
            System.out.println();
            System.out.println(firstLine);
            System.out.println(secondLine);
            System.out.println(queryTokenTableWriter.writeQueryTokenTable(tokens));
            tokens.clear();
        }
    }

    private static void processFile(final String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        process(new String(bytes, Charset.defaultCharset()));
    }

    private static void process(final String programSource) {
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