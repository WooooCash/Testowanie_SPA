package ats.v1.pkb.uses_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsesTableImpl implements UsesTable {
    private Map<Integer, List<Integer>> used = new HashMap<>();
    private Map<Integer, List<Integer>> uses = new HashMap<>();

    /**
     * Sets a relationship which describes a variable being used in a specified statement.
     *
     * @param statementLineNr The line number of the statement.
     * @param varIdx          A variable index (referencing the <strong>varTable</strong>).
     */
    @Override
    public void setUses(int statementLineNr, int varIdx) {
        List<Integer> varList = getMapValueList(used, statementLineNr);
        List<Integer> statementList = getMapValueList(uses, varIdx);

        if (!varList.contains(varIdx))
            varList.add(varIdx);
        if (!statementList.contains(statementLineNr))
            statementList.add(statementLineNr);
    }

    @Override
    public void setUses(int statementLineNr, List<Integer> varIndices) {
        if (varIndices == null) return;
        for (int varIdx : varIndices) {
            setUses(statementLineNr, varIdx);
        }
    }

    /**
     * Returns a list of variable used by the provided statement.
     *
     * @param statementLineNr The line number of the statement.
     * @return A list of variable indexes (referencing the <strong>varTable</strong>).
     */
    @Override
    public List<Integer> getUsed(int statementLineNr) {
        try {
            return used.get(statementLineNr);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * Returns a list of statements which use the provided variable
     *
     * @param varIdx A variable index (referencing the <strong>varTable</strong>).
     * @return A list of line numbers which correspond to the statements.
     */
    @Override
    public List<Integer> getUses(int varIdx) {
        try {
            return uses.get(varIdx);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public boolean isUsed(int varIdx, int statementLineNr) {
        try {
            List<Integer> varList = used.get(statementLineNr);
            return varList.contains(varIdx);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private List<Integer> getMapValueList(Map<Integer, List<Integer>> map, int key) {
        if (!map.containsKey(key))
            map.put(key, new ArrayList<>());

        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder tableStr = new StringBuilder("USES TABLE\n");
        tableStr.append("__________________________\n");
        tableStr.append(String.format("| %10s | %8s \n", "STATEMENT", "VARS"));
        for (int statementLine : used.keySet()) {
            StringBuilder vars = new StringBuilder();
            for (int varIdx : used.get(statementLine))
                vars.append(varIdx + ", ");
            tableStr.append(String.format("| %10d | %8s \n", statementLine, vars.toString()));
        }
        tableStr.append("--------------------------\n");
        return tableStr.toString();
    }
}
