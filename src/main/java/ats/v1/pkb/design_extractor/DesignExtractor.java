package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.ProcedureNode;
import ats.v1.pkb.call_table.CallsTable;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;

public class DesignExtractor {
    private final Ast ast;
    private int currentProcIdx = -1;

    public DesignExtractor(Ast ast) {
        this.ast = ast;
    }

    public void extractModifies(ModifiesTable mtable) {
        traverse(ast.getRoot(), new ModifiesExtractor(ast, mtable));
    }

    public void extractUses(UsesTable utable) {
        traverse(ast.getRoot(), new UsesExtractor(ast, utable));
    }

    public void extractModifiesUsesForCall(ModifiesTable mtable, UsesTable utable, StatementTable stable) {
        traverse(ast.getRoot(), new ModifiesUsesForCallExtractor(utable, mtable, stable));
    }

    public void extractModifiesUsesForProcedure(ModifiesTable mtable, UsesTable utable, StatementTable stable, CallsTable ctable) {
        traverse(ast.getRoot(), new ModifiesUsesForProcedureExtractor(mtable, utable, ctable, stable));
    }

    public void extractParent() {
        traverse(ast.getRoot(), new ParentExtractor());
    }

    public void extractCalls(CallsTable ctable) {
        traverse(ast.getRoot(), new CallsExtractor(ctable));
    }

    private void traverse(Node root, Extractor extractor) {
        if (root == null) return;

        if (root instanceof ProcedureNode) {
            ProcedureNode procedureNode = (ProcedureNode) root;
            currentProcIdx = procedureNode.getProcIdx();
        }

        for (Node child : root.getChildren())
            traverse(child, extractor);
        if (extractor.check(root)) {
            extractor.extract(root, currentProcIdx);
        }

    }

}
