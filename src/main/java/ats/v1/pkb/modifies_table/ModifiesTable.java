package ats.v1.pkb.modifies_table;

import java.util.List;

public interface ModifiesTable {
    void setModifies(int statementLineNr, int varIdx);
    void setModifies(int statementLineNr, List<Integer> varIndices);
    List<Integer> getModified(int statementLineNr);
    List<Integer> getModifies(int varIdx);
    boolean isModified(int varIdx, int statementLineNr);
}
