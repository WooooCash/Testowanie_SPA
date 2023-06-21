package ats.v1.pkb;

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
public class PkbProcessor {

    public Pkb process(final String path) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String programSource = new String(bytes, Charset.defaultCharset());

        Scanner scanner = new Scanner(programSource);
        List<Token> tokens = scanner.scanTokens();

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
        return new PKB_Impl(modifiesTable, usesTable, varTable, statTable);
    }

}
