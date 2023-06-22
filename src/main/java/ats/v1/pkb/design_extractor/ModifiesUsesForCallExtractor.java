package ats.v1.pkb.design_extractor;

import ats.v1.pkb.ast.nodes.CallNode;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.ProcedureNode;
import ats.v1.pkb.ast.nodes.StatementNode;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ModifiesUsesForCallExtractor implements Extractor {
    private UsesTable utable;
    private ModifiesTable mtable;
    private StatementTable stable;

    @Override
    public boolean check(Node node) {
        return node instanceof CallNode;
    }

    @Override
    public void extract(Node node, int currentProcIdx) {
        CallNode cnode = (CallNode) node;
        ProcedureNode calledProcedure = getCalledProcedure(cnode);
        if (calledProcedure == null) return;

        List<Integer> modifiedVars = mtable.getModified(calledProcedure.getLine());
        mtable.setModifies(cnode.getLine(), modifiedVars);

        List<Integer> usedVars = utable.getUsed(calledProcedure.getLine());
        utable.setUses(cnode.getLine(), usedVars);
    }

    private ProcedureNode getCalledProcedure(CallNode cnode) {
        List<StatementNode> allStatements = stable.getStatements();
        for (StatementNode sn : allStatements) {
            if (!(sn instanceof ProcedureNode)) continue;
            ProcedureNode pn = (ProcedureNode) sn;
            if (pn.getProcIdx() == cnode.getProcIdx())
                return pn;
        }
        return null;
    }
}
