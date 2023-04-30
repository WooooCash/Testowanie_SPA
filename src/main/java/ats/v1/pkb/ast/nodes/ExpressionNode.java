package ats.v1.pkb.ast.nodes;

public class ExpressionNode extends Node {
    private String operator;

    public ExpressionNode(String operator) {
        this.operator = operator;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - Operator: " + operator;
    }
}
