package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.AssignNode;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.StatementNode;
import ats.v1.pkb.ast.nodes.VarNode;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class UsesExtractor implements Extractor {
    private Ast ast;
    private UsesTable utable;

    @Override
    public boolean check(Node node) {
        return node instanceof AssignNode;
    }

    @Override
    public void extract(Node node) {
        StatementNode statement = (StatementNode)node;
        Node rightSide = ast.getNthChild(statement, 2);
        traverse(rightSide, statement.getLine());
    }

    private void traverse(Node node, int lineNr) {
        if (node == null) return;
        if (node instanceof VarNode) {
            VarNode var = (VarNode)node;
            utable.setUses(lineNr, var.getVarIdx());
        }

        for (Node child : node.getChildren())
            traverse(child, lineNr);
    }
}
