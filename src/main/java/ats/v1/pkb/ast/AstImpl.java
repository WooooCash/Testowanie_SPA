package ats.v1.pkb.ast;

import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.NodeType;

import java.util.List;

public class AstImpl implements Ast {
    private Node astRoot;

    public AstImpl(Node astRoot) {
        this.astRoot = astRoot;
    }

    @Override
    public Node createNode(final NodeType type) {
        return null;
    }

    @Override
    public void setFirstChild(final Node parent, final Node child) {
        parent.getChildren().add(0, child);
        child.setParent(parent);
    }

    @Override
    public void addChild(Node parent, Node child) {
        parent.getChildren().add(child);
        child.setParent(parent);
    }

    @Override
    public void setChildren(Node parent, List<Node> children) {
        parent.setChildren(children);
        for (Node child : children)
            child.setParent(parent);
    }

    @Override
    public Node getRoot() {
        return astRoot;
    }

    /**
     * Returns the nth child of a parent node.
     * @param parent The node from which the selection is made.
     * @param n The number of the child node - The first node being n=1.
     * @return The selected child node, or null if n exceeds the number of the parent node's children.
     */
    @Override
    public Node getNthChild(final Node parent, final int n) {
        if (n >= parent.getChildren().size()-1)
            return null;

        return parent.getChildren().get(n+1);
    }
}
