package ats.v1.pkb.ast;

import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.NodeType;

import java.util.List;

public interface Ast {
    Node createNode(NodeType type);
    void setFirstChild(Node parent, Node child);
    void addChild(Node parent, Node child);
    void setChildren(Node parent, List<Node> children);

    Node getRoot();
    Node getNthChild(Node parent, int n);
}
