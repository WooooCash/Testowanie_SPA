package ats.v1.pkb.ast.nodes;

import lombok.Getter;

@Getter
public class VarNode extends Node {
    private final int varIdx;

    public VarNode(int varIdx) {
        super();
        this.varIdx = varIdx;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - index: " + varIdx;
    }
}
