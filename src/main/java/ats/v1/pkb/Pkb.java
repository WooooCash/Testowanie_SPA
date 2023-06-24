package ats.v1.pkb;

import java.util.List;

public interface Pkb {

    boolean modifies(int statement, String var);

    List<String> modifies(int statement);

    List<Integer> modifies(String var, String type);

    boolean uses(int statement, String var);

    List<String> uses(int statement);

    List<Integer> uses(String var, String type);

    boolean follows(int s1, int s2);

    int follows_after(int s1, String type);

    int follows_before(int s2, String type);

    boolean isParent(int s1, int s2);

    List<Integer> getChild(int s1, String type);

    int getParent(int s2, String type);

    public boolean calls(String p1, String p2);

    public List<String> calls(String p2);

    public List<String> calledFrom(String p1);

    List<Integer> filterStatements(List<Integer> all, String type);

    List<Integer> getStatements(String type);

    List<String> getStringTypes(String type);

    List<String> getProcedureNamesByLines(List<Integer> ints);
}


/*
123 - liczba (stmt)
abc - argument w pql
"abc" - nazwa zmeinnej w programie smpl



 */