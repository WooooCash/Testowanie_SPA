package ats.v1;

import ats.v1.pkb.PKB;
import ats.v1.pkb.Parser;
import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.design_extractor.DesignExtractor;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.modifies_table.ModifiesTableImpl;
import ats.v1.pkb.proc_table.ProcTable;
import ats.v1.pkb.proc_table.ProcTableImpl;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.statement_table.StatementTableImpl;
import ats.v1.pkb.uses_table.UsesTable;
import ats.v1.pkb.uses_table.UsesTableImpl;
import ats.v1.pkb.var_table.VarTable;
import ats.v1.pkb.var_table.VarTableImpl;
import ats.v1.spa_frontend.scanner.Scanner;
import ats.v1.spa_frontend.token.Token;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Test");
        try {
            processFile(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ready");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        while (sc.hasNextLine()) {
            String declaration = sc.nextLine();
            String queryString = sc.nextLine();
            if (queryString.length() == 0) break;
            System.out.println("FALSE " + declaration + " " + queryString);
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
        ModifiesTable modifiesTable = new ModifiesTableImpl();
        UsesTable usesTable = new UsesTableImpl();
        StatementTable statTable = new StatementTableImpl();
        Parser parser = new Parser(tokens, varTable, procTable, statTable);
        Ast ast = parser.parseTokens();
        DesignExtractor extractor = new DesignExtractor(ast);
        extractor.extractModifies(modifiesTable);
        extractor.extractUses(usesTable);
        PKB pkb = new PKB(modifiesTable, usesTable, varTable, statTable);
        System.out.println(ast.getRoot().toString());
        System.out.println(procTable);
        System.out.println(varTable);
        System.out.println(modifiesTable);
        System.out.println(usesTable);


    }
}