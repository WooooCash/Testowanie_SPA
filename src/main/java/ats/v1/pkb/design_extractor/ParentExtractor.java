package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.StatementListNode;

public class ParentExtractor implements Extractor {
    @Override
    public boolean check(Node node) {
        return node instanceof StatementListNode;
    }

    @Override
    public void extract(Node node, int currentProcIdx) {
        for (Node child : node.getChildren()) {
            child.setParent(node.getParent());
        }
    }
}
