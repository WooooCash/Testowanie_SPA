package ats.v1.pkb.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VarNode extends Node {
    private int index;

    public VarNode(NodeType type, int index) {
        super(type);
        this.index = index;
    }
}
