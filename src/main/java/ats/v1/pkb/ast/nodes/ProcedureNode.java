package ats.v1.pkb.ast.nodes;

import lombok.Getter;

@Getter
public class ProcedureNode extends StatementNode {
    private final int procIdx;

    public ProcedureNode(int line, int procIdx) {
        super(line);
        this.procIdx = procIdx;
    }

    @Override
    protected String nodeName() {
        return super.nodeName() + " - Index: " + procIdx;
    }
}
