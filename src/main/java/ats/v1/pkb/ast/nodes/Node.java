package ats.v1.pkb.ast.nodes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Node {
    private ArrayList<Node> children;
    private Node leftSibling;
    private Node rightSibling;
    private Node parent;
    private Node follows;

}
