package ats.v1.pkb.call_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallsTableImpl implements CallsTable {
    private Map<Integer, List<Integer>> calledFrom = new HashMap<>();
    private Map<Integer, List<Integer>> calls = new HashMap<>();


    @Override
    public void setCalls(int proc1, int proc2) {
        List<Integer> callsList = getMapValueList(calls, proc2);
        List<Integer> calledFromList = getMapValueList(calledFrom, proc1);

        if (!calledFromList.contains(proc2))
            calledFromList.add(proc2);
        if (!callsList.contains(proc1))
            callsList.add(proc1);
    }


    @Override
    public List<Integer> getCalledFrom(int proc) {
        try {
            return calledFrom.get(proc);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }


    @Override
    public List<Integer> getCalls(int proc) {
        try {
            return calls.get(proc);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public boolean doesCall(int proc1, int proc2) {
        try {
            List<Integer> procList = calledFrom.get(proc1);
            return procList.contains(proc2);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private List<Integer> getMapValueList(Map<Integer, List<Integer>> map, int key) {
        if (!map.containsKey(key))
            map.put(key, new ArrayList<>());

        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder tableStr = new StringBuilder("CALLS TABLE\n");
        tableStr.append("__________________________\n");
        tableStr.append(String.format("| %10s | %8s \n", "PROCEDURE", "CALLS"));
        for (int procIdx : calledFrom.keySet()) {
            StringBuilder procs = new StringBuilder();
            for (int proc2Idx : calledFrom.get(procIdx))
                procs.append(proc2Idx + ", ");
            tableStr.append(String.format("| %10d | %8s \n", procIdx, procs.toString()));
        }
        tableStr.append("--------------------------\n");
        return tableStr.toString();
    }
}
