package ats.v1.pkb.ast.nodes;

import lombok.Getter;

@Getter
public class CallNode extends StatementNode {
    private int procIdx;

    public CallNode(int line, int procIdx) {
        super(line);
        this.procIdx = procIdx;
    }

    @Override
    public String nodeName() {
        return super.nodeName() + " | Procedure: " + procIdx;
    }
}
