package ats.v1.pkb.var_table;

public interface VarTable {
    int insert(String name);
    String getVarName(int idx);
    int getVarIndex(String name);
    boolean contains(String name);
}
