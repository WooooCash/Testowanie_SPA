package ats.v1.pkb.statement_table;

import ats.v1.pkb.design_extractor.StatementType;

import java.util.HashMap;
import java.util.Map;

public class StatementTableImpl implements StatementTable {
    private final Map<Integer, StatementType> statements = new HashMap<>();

    @Override
    public void addStatement(int lineNr, StatementType type) {
        statements.put(lineNr, type);
    }

    @Override
    public StatementType getStatementType(int lineNr) {
        if (!statements.containsKey(lineNr)) {
            return null;
        }
        return statements.get(lineNr);
    }
}
