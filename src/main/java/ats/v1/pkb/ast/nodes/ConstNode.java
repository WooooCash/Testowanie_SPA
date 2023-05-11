package ats.v1.pkb.ast.nodes;

public class ConstNode extends Node {
    private int value;

    public ConstNode(int value) {
        this.value = value;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - Value: " + value;
    }
}
