package ats.v1.pkb.proc_table;

public interface ProcTable {
    int insert(String name);
    String getProcName(int idx);
    int getProcIndex(String name);
    boolean contains(String name);
}
