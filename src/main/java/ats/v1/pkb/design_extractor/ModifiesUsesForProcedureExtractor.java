package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.call_table.CallsTable;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ModifiesUsesForProcedureExtractor implements Extractor {
    ModifiesTable mtable;
    UsesTable utable;
    CallsTable ctable;
    StatementTable stable;

    @Override
    public boolean check(Node node) {
        return node instanceof ProcedureNode;
    }

    @Override
    public void extract(Node node, int currentProcIdx) {
        ProcedureNode pnode = (ProcedureNode) node;
        updateHigherProcedures(pnode);
    }

    private void updateHigherProcedures(ProcedureNode pnode) {
        List<Integer> called = ctable.getCalls(pnode.getProcIdx());
        if (called == null) return;
        List<StatementNode> allStatements = stable.getStatements();
        for (StatementNode sn : allStatements) {
            if (!(sn instanceof ProcedureNode)) continue;
            ProcedureNode pn = (ProcedureNode) sn;
            if (called.contains(pn.getProcIdx())) {
                mtable.setModifies(pn.getLine(), mtable.getModified(pnode.getLine()));
                utable.setUses(pn.getLine(), utable.getUsed(pnode.getLine()));
                updateHigherProcedures(pn);
            }
        }
    }
}
