package ats.v1.pkb.proc_table;

import java.util.HashMap;

public class ProcTableImpl implements ProcTable {
    HashMap<Integer, String> procsByIdx = new HashMap<>();
    HashMap<String, Integer> procsByName = new HashMap<>();

    @Override
    public int insert(String name) {
        if (contains(name))
            return getProcIndex(name);

        int newIdx = procsByIdx.size();
        procsByIdx.put(newIdx, name);
        procsByName.put(name, newIdx);
        return newIdx;
    }

    @Override
    public String getProcName(int idx) {
        if (!procsByIdx.containsKey(idx))
            return null;
        return procsByIdx.get(idx);
    }

    @Override
    public int getProcIndex(String name) {
        if (!contains(name))
            return -1;
        return procsByName.get(name);
    }

    @Override
    public boolean contains(String name) {
        return procsByName.containsKey(name);
    }

    @Override
    public String toString() {
        StringBuilder tableStr = new StringBuilder("Proc TABLE\n");
        tableStr.append("_____________________\n");
        tableStr.append(String.format("| %6s | %8s |\n", "INDEX", "NAME"));
        for (int key : procsByIdx.keySet()) {
            tableStr.append(String.format("| %6d | %8s |\n", key, procsByIdx.get(key)));
        }
        tableStr.append("---------------------\n");
        return tableStr.toString();
    }
}
