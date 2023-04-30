package ats.v1.pkb.ast.nodes;

public class ExpressionNode extends Node {
    private char operator;

    public ExpressionNode(char operator) {
        this.operator = operator;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - Operator: " + operator;
    }
}
