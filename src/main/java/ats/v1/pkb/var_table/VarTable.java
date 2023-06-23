package ats.v1.pkb.var_table;

import java.util.List;

public interface VarTable {
    List<String> getAll();
    int insert(String name);
    String getName(int idx);
    int getIndexOf(String name);
}
