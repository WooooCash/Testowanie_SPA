package ats.v1.pkb;

import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.call_table.CallsTable;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.proc_table.ProcTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import ats.v1.pkb.var_table.VarTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class PKB_Impl implements Pkb {
    private static final Map<String, Type> statementMapping;

    static {
        statementMapping = new HashMap<>();
        statementMapping.put("stmt", StatementNode.class);
        statementMapping.put("assign", AssignNode.class);
        statementMapping.put("while", WhileNode.class);
        statementMapping.put("call", CallNode.class);
        statementMapping.put("procedure", ProcedureNode.class);
    } //TODO wynieść do zewnętrznej klasy

    private ModifiesTable mtable;
    private UsesTable utable;
    private VarTable varTable;
    private StatementTable statTable;
    private CallsTable ctable;
    private ProcTable ptable;

    public boolean modifies(int statement, String var) {
        return mtable.isModified(varTable.getIndexOf(var), statement);
    }

    public List<String> modifies(int statement) {
        List<Integer> idxList = mtable.getModified(statement);
        return castToEmpty(idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList()));
    }

    public List<Integer> modifies(String var, String type) {
        List<Integer> allStatements = mtable.getModifies(varTable.getIndexOf(var));
        return castToEmpty(filterStatements(allStatements, type));
    }

    public boolean uses(int statement, String var) {
        return utable.isUsed(varTable.getIndexOf(var), statement);
    }

    public List<String> uses(int statement) {
        List<Integer> idxList = utable.getUsed(statement);
        return castToEmpty(idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList()));
    }

    public List<Integer> uses(String var, String type) {
        List<Integer> allStatements = utable.getUses(varTable.getIndexOf(var));
        return castToEmpty(filterStatements(allStatements, type));
    }

    public boolean follows(int s1, int s2) {
        StatementNode statement1 = statTable.getStatement(s1);
        StatementNode statement2 = statTable.getStatement(s2);
        if (statement1 == null || statement2 == null) return false;
        return statement2.getFollows() == statement1;
    }

    public int follows_after(int s1, String type) {
        StatementNode statement1 = statTable.getStatement(s1);
        if (statement1 == null) return -1;
        for (StatementNode statement : statTable.getStatements()) {
            if (statement.getFollows() == statement1 && checkType(statement, type)) {
                return statement.getLine();
            }
        }
        return -1;
    }

    public int follows_before(int s2, String type) {
        StatementNode statement1 = statTable.getStatement(s2);
        if (statement1 == null) return -1;
        Node before = statement1.getFollows();
        if (before != null && checkType(before, type)) {
            return ((StatementNode) before).getLine();
        }

        return -1;
    }

    @Override
    public boolean isParent(int s1, int s2) {
        StatementNode statement1 = statTable.getStatement(s1);
        StatementNode statement2 = statTable.getStatement(s2);
        if (statement1 == null || statement2 == null) return false;
        return statement2.getParent() == statement1;
    }

    @Override
    public List<Integer> getChild(int s1, String type) {
        List<Integer> result = new ArrayList<>();
        StatementNode statement1 = statTable.getStatement(s1);
        if (statement1.getChildren().isEmpty()) return result;
        if (statement1.getChildren().get(0) instanceof StatementListNode) {
            List<Node> children = statement1.getChildren().get(0).getChildren();
            for (Node n : children) {
                if (!(n instanceof StatementNode)) continue;
                result.add(((StatementNode)n).getLine());
            }
        } else {
            List<Node> children = statement1.getChildren();
            for (Node n : children) {
                if (!(n instanceof StatementNode)) continue;
                result.add(((StatementNode)n).getLine());
            }
        }
        return result;
    }

    @Override
    public int getParent(int s2, String type) {
        StatementNode statement1 = statTable.getStatement(s2);
        if (statement1 == null) return -1;
        Node parent = statement1.getParent();
        if (parent != null && checkType(parent, type)) {
            return ((StatementNode) parent).getLine();
        }

        return -1;
    }

    public boolean calls(String p1, String p2) {
        int proc1 = ptable.getIndexOf(p1);
        int proc2 = ptable.getIndexOf(p2);
        if (proc1 == -1 || proc2 == -1) return false;
        return ctable.doesCall(proc1, proc2);
    }

    public List<String> calls(String p2) {
        int procIdx = ptable.getIndexOf(p2);
        List<Integer> procs = ctable.getCalls(procIdx);
        List<String> result = new ArrayList<>();
        for (int p : procs) {
            result.add(ptable.getName(p));
        }
        return result;
    }

    public List<String> calledFrom(String p1) {
        int procIdx = ptable.getIndexOf(p1);
        List<Integer> procs = ctable.getCalledFrom(procIdx);
        List<String> result = new ArrayList<>();
        for (Integer p : procs) {
            result.add(ptable.getName(p));
        }
        return result;
    }

    @Override
    public List<Integer> getStatements(String type) {
        List<Integer> result = new ArrayList<>();
        for (StatementNode sn : statTable.getStatements()) {
            if (checkType(sn, type)) result.add(sn.getLine());
        }
        return result;
    }

    public List<String> getStringTypes(String type) {
        if (type.equals("variable")) {
            return varTable.getAll();
        } else if (type.equals("procedure")) {
            return ptable.getAll();
        }
        return new ArrayList<>();
    }

    public List<String> getProcedureNamesByLines(List<Integer> ints) {
        List<String> result = new ArrayList<>();
        for (Integer i : ints) {
            StatementNode sn = statTable.getStatement(i);
            if (!(sn instanceof ProcedureNode)) continue;
            ProcedureNode pn = (ProcedureNode) sn;
            result.add(ptable.getName(pn.getProcIdx()));
        }
        return result;
    }

    public List<Integer> filterStatements(List<Integer> all, String type) {
        if (type == null || all == null) {
            return all;
        }
        return all
                .stream()
                .filter(stmt -> checkType(statTable.getStatement(stmt), type))
                .collect(Collectors.toList());
    }


    private boolean checkType(Node s, String type) {
        if (type.equals("prog_line")) return s instanceof StatementNode;
        if (type.equals("stmt")) return s.getClass() != ProcedureNode.class && s instanceof StatementNode;
        if (type.equals("assign")) return s instanceof AssignNode;
        if (type.equals("while")) return s instanceof WhileNode;
        if (type.equals("if")) return s instanceof IfNode;
        if (type.equals("call")) return s instanceof CallNode;
        if (type.equals("procedure")) return s instanceof ProcedureNode;
        return false;
    }


    private <T> List<T> castToEmpty(List<T> list) {
        return list != null ? list : new ArrayList<>();
    }
}
