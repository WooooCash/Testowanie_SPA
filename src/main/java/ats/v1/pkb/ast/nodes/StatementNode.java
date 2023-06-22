package ats.v1.pkb.ast.nodes;

import lombok.Getter;

@Getter
public class StatementNode extends Node {
    private final int line;

    public StatementNode(int line) {
        super();
        this.line = line;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - Line: " + line;
    }
}
