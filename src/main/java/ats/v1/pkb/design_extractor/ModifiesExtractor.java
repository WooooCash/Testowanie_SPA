package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.modifies_table.ModifiesTable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModifiesExtractor implements Extractor {
    Ast ast;
    ModifiesTable mtable;

    @Override
    public boolean check(Node node) {
        return node instanceof AssignNode;
    }

    @Override
    public void extract(Node node) {
        StatementNode statement = (StatementNode)node;
        VarNode var = (VarNode)ast.getNthChild(node, 1);
        mtable.setModifies(statement.getLine(), var.getVarIdx());

        updateParents(statement.getParent(), var.getVarIdx());
    }

    private void updateParents(Node parent, int varIdx) {
        if (parent == null) return;
        if (!(parent instanceof WhileNode)) return;
        StatementNode statement = (WhileNode) parent;
        mtable.setModifies(statement.getLine(), varIdx);
        updateParents(statement.getParent(), varIdx);
    }
}
