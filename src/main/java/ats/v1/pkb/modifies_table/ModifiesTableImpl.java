package ats.v1.pkb.modifies_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifiesTableImpl implements ModifiesTable {
    Map<Integer, List<Integer>> modified = new HashMap<>();
    Map<Integer, List<Integer>> modifies = new HashMap<>();

    /**
     * Sets a relationship which describes a variable being modified by a specified statement.
     *
     * @param statementLineNr The line number of the modifying statement.
     * @param varIdx          A variable index (referencing the <strong>varTable</strong>).
     */
    @Override
    public void setModifies(int statementLineNr, int varIdx) {
        List<Integer> varList = modified.containsKey(statementLineNr) ?
                modified.get(statementLineNr) : modified.put(statementLineNr, new ArrayList<>());
        List<Integer> statementList = modifies.containsKey(varIdx) ?
                modifies.get(varIdx) : modifies.put(varIdx, new ArrayList<>());

        if (!varList.contains(varIdx))
            varList.add(varIdx);
        if (!statementList.contains(statementLineNr))
            statementList.add(statementLineNr);
    }

    /**
     * Returns a list of variable modified by the provided statement (assignments have one, while/procedure have multiple).
     *
     * @param statementLineNr The line number of the modifying statement.
     * @return A list of variable indexes (referencing the <strong>varTable</strong>).
     */
    @Override
    public List<Integer> getModified(int statementLineNr) {
        try {
            return modified.get(statementLineNr);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * Returns a list of statements which modify the provided variable
     *
     * @param varIdx A variable index (referencing the <strong>varTable</strong>).
     * @return A list of line numbers which correspond to the modifying statements.
     */
    @Override
    public List<Integer> getModifies(int varIdx) {
        try {
            return modifies.get(varIdx);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public boolean isModified(int varIdx, int statementLineNr) {
        try {
            List<Integer> varList = modified.get(statementLineNr);
            return varList.contains(varIdx);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
