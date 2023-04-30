package ats.v1.pkb.proc_table;

public interface ProcTable {
    int insert(String name);
    String getName(int idx);
    int getIndexOf(String name);
}
