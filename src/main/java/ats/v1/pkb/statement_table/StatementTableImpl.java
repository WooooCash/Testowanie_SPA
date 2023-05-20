package ats.v1.pkb.statement_table;

import ats.v1.pkb.ast.nodes.StatementNode;
import ats.v1.pkb.design_extractor.StatementType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementTableImpl implements StatementTable {
    private Map<Integer, StatementNode> statements = new HashMap<>();

    @Override
    public void addStatement(StatementNode node) {
        statements.put(node.getLine(), node);
    }

    @Override
    public StatementNode getStatement(int lineNr) {
        if (!statements.containsKey(lineNr)) {
            return null;
        }
        return statements.get(lineNr);
    }

    @Override
    public List<StatementNode> getStatements() {
        return (List<StatementNode>)statements.values();
    }
}
