package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.nodes.Node;

public interface Extractor {
    boolean check(Node node);
    void extract(Node node, int currentProcIdx);
}
