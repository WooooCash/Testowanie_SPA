package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DesignExtractor {
    private final Ast ast;

    public void extractModifies(ModifiesTable mtable) {
        traverse(ast.getRoot(), new ModifiesExtractor(ast, mtable));
    }

    public void extractUses(UsesTable utable) {
        traverse(ast.getRoot(), new UsesExtractor(ast, utable));
    }

    private void traverse(Node root, Extractor extractor) {
        if (root == null) return;

        if (extractor.check(root))
            extractor.extract(root);

        for (Node child : root.getChildren())
            traverse(child, extractor);
    }

}
