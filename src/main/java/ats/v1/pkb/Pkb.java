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

    public boolean calls(int p1, int p2);

    public List<Integer> calls(int p2);

    public List<Integer> calledFrom(int p1);

    List<Integer> filterStatements(List<Integer> all, String type);
}


/*
123 - liczba (stmt)
abc - argument w pql
"abc" - nazwa zmeinnej w programie smpl



 */