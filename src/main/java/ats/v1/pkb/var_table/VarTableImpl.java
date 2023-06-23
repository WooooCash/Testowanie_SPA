package ats.v1.pkb.var_table;

import java.util.ArrayList;
import java.util.List;

public class VarTableImpl implements VarTable {
    List<String> table = new ArrayList<>();

    @Override
    public List<String> getAll() {
        return table;
    }

    @Override
    public int insert(String name) {
        if (table.contains(name))
            return table.indexOf(name);

        table.add(name);
        return table.indexOf(name);
    }

    @Override
    public String getName(int idx) {
        return table.get(idx);
    }

    @Override
    public int getIndexOf(String name) {
        return table.indexOf(name);
    }


    @Override
    public String toString() {
        StringBuilder tableStr = new StringBuilder("VAR TABLE\n");
        tableStr.append("_____________________\n");
        tableStr.append(String.format("| %6s | %8s |\n", "INDEX", "NAME"));
        for (int i = 0; i < table.size(); i++) {
            tableStr.append(String.format("| %6d | %8s |\n", i, table.get(i)));
        }
        tableStr.append("---------------------\n");
        return tableStr.toString();
    }
}
