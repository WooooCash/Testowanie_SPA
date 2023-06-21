package ats.v1.pkb.call_table;

import java.util.List;

public interface CallsTable {
    void setCalls(int proc1, int proc2);
    List<Integer> getCalls(int proc);
    List<Integer> getCalledFrom(int proc);
    boolean doesCall(int proc1, int proc2);
}
