package ats.v1.pkb.var_table;

import java.util.HashMap;

public class VarTableImpl implements VarTable {
    HashMap<Integer, String> varsByIdx = new HashMap<>();
    HashMap<String, Integer> varsByName = new HashMap<>();

    @Override
    public int insert(String name) {
        if (contains(name))
            return getVarIndex(name);

        int newIdx = varsByIdx.size();
        varsByIdx.put(newIdx, name);
        varsByName.put(name, newIdx);
        return newIdx;
    }

    @Override
    public String getVarName(int idx) {
        if (!varsByIdx.containsKey(idx))
            return null;
        return varsByIdx.get(idx);
    }

    @Override
    public int getVarIndex(String name) {
        if (!contains(name))
            return -1;
        return varsByName.get(name);
    }

    @Override
    public boolean contains(String name) {
        return varsByName.containsKey(name);
    }

    @Override
    public String toString() {
        StringBuilder tableStr = new StringBuilder("VAR TABLE\n");
        tableStr.append("_____________________\n");
        tableStr.append(String.format("| %6s | %8s |\n", "INDEX", "NAME"));
        for (int key : varsByIdx.keySet()) {
            tableStr.append(String.format("| %6d | %8s |\n", key, varsByIdx.get(key)));
        }
        tableStr.append("---------------------\n");
        return tableStr.toString();
    }
}
