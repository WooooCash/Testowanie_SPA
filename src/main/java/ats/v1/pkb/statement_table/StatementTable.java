package ats.v1.pkb.statement_table;

import ats.v1.pkb.ast.nodes.StatementNode;

import java.util.List;

public interface StatementTable {
    void addStatement(StatementNode type);
    StatementNode getStatement(int lineNr);
    List<StatementNode> getStatements();
}
