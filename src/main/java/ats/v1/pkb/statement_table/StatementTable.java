package ats.v1.pkb.statement_table;

import ats.v1.pkb.design_extractor.StatementType;

public interface StatementTable {
    void addStatement(int lineNr, StatementType type);
    StatementType getStatementType(int lineNr);
}
