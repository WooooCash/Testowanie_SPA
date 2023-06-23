package ats.v1.pkb;

import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.call_table.CallsTable;
import ats.v1.pkb.modifies_table.ModifiesTable;
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
public class PKB_Impl implements Pkb{
    private ModifiesTable mtable;
    private UsesTable utable;
    private VarTable varTable;
    private StatementTable statTable;
    private CallsTable ctable;

    private static final Map<String, Type> statementMapping;
    static {
        statementMapping = new HashMap<>();
        statementMapping.put("stmt", StatementNode.class);
        statementMapping.put("assign", AssignNode.class);
        statementMapping.put("while", WhileNode.class);
        statementMapping.put("call", CallNode.class);
        statementMapping.put("procedure", ProcedureNode.class);
    } //TODO wynieść do zewnętrznej klasy

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
            return ((StatementNode)before).getLine();
        }

        return -1;
    }

    public boolean calls(int p1, int p2) {
        return ctable.doesCall(p1, p2);
    }

    public List<Integer> calls(int p2) {
        return ctable.getCalls(p2);
    }

    public List<Integer> calledFrom(int p1) {
        return ctable.getCalledFrom(p1);
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
