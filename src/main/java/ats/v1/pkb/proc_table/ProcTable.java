package ats.v1.pkb.proc_table;

import java.util.List;

public interface ProcTable {
    List<String> getAll();
    int insert(String name);
    String getName(int idx);
    int getIndexOf(String name);
}
