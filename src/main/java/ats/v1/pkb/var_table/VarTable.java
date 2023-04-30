package ats.v1.pkb.var_table;

public interface VarTable {
    int insert(String name);
    String getName(int idx);
    int getIndexOf(String name);
}
