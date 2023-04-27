package ats.v1.pkb.ast;

import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.NodeType;

public interface Ast {
    Node createNode(NodeType type);
    void setNthChild(Node parent, Node child);

    Node getRoot();
    Node getNthChild(Node parent, int n);
}
