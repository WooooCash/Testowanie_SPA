package ats.v1.pkb.uses_table;

import java.util.List;

public interface UsesTable {
    void setUses(int statementLineNr, int varIdx);
    void setUses(int statementLineNr, List<Integer> varIndices);
    List<Integer> getUsed(int statementLineNr);
    List<Integer> getUses(int varIdx);
    boolean isUsed(int varIdx, int statementLineNr);
}
