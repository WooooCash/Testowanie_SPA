package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.call_table.CallsTable;
import ats.v1.pkb.modifies_table.ModifiesTable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallsExtractor implements Extractor {
    private CallsTable callsTable;

    @Override
    public boolean check(Node node) {
        return node instanceof CallNode;
    }

    @Override
    public void extract(Node node, int currentProcIdx) {
        CallNode call = (CallNode) node;
        int procIdx = call.getProcIdx();
        callsTable.setCalls(currentProcIdx, procIdx);
    }
}
