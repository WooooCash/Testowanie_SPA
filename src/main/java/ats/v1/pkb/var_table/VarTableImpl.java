package ats.v1.pkb.var_table;

import java.util.ArrayList;
import java.util.List;

public class VarTableImpl implements VarTable {
    List<String> table = new ArrayList<>();

    @Override
    public int insert(String name) {
        if (table.contains(name))
            return -1;

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
}
