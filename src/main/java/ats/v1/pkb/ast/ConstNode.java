package ats.v1.pkb.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstNode extends Node {
    private int value;

    public ConstNode(NodeType type, int value) {
        super(type);
        this.value = value;
    }
}
