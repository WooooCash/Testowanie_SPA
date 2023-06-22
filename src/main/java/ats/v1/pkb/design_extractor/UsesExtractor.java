package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UsesExtractor implements Extractor {
    private Ast ast;
    private UsesTable utable;

    @Override
    public boolean check(Node node) {
        return node instanceof AssignNode
                || node instanceof WhileNode
                || node instanceof IfNode;
    }

    @Override
    public void extract(Node node, int currentProcIdx) {
        StatementNode statement = (StatementNode)node;
        if (node instanceof AssignNode) {
            Node rightSide = ast.getNthChild(statement, 2);
            traverse(rightSide, statement.getLine());
        } else {
            VarNode condition = (VarNode) ast.getNthChild(statement, 1);
            utable.setUses(statement.getLine(), condition.getVarIdx());
        }

        updateParents(statement.getParent(), utable.getUsed(statement.getLine()));
    }

    private void traverse(Node node, int lineNr) {
        if (node == null) {
            return;
        }
        if (node instanceof VarNode) {
            VarNode var = (VarNode)node;
            utable.setUses(lineNr, var.getVarIdx());
        }

        for (Node child : node.getChildren())
            traverse(child, lineNr);
    }

    private void updateParents(Node parent, List<Integer> varIndices) {
        if (parent == null || varIndices == null) {
            return;
        }
        if (parent instanceof StatementListNode) {
            updateParents(parent.getParent(), varIndices);
            return;
        }
        if (!(parent instanceof WhileNode) && !(parent instanceof IfNode) && !(parent instanceof ProcedureNode)) return;
        StatementNode statement = (StatementNode) parent;
        utable.setUses(statement.getLine(), varIndices);
        updateParents(statement.getParent(), varIndices);
    }
}
