package ats.v1.pkb.ast;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Node {
    private NodeType type;
    private List<Node> children;
    private Node leftSibling;
    private Node rightSibling;
    private Node parent;
    private Node follows;

    public Node(NodeType type) {
        this.type = type;
    }
}
