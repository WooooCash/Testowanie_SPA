package ats.v1.pkb.ast;

public interface Ast {
    Node createNode(NodeType type);
    void setNthChild(Node parent, Node child);

    Node getRoot();
    Node getNthChild(Node parent, int n);
}
