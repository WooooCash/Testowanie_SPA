package ats.v1.pkb;

import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.StatementNode;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import ats.v1.pkb.var_table.VarTable;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PKB {
    private ModifiesTable mtable;
    private UsesTable utable;
    private VarTable varTable;
    private StatementTable statTable;

    public boolean modifies(int statement, String var) {
        return mtable.isModified(varTable.getIndexOf(var), statement);
    }

    public List<String> modifies(int statement) {
        List<Integer> idxList = mtable.getModified(statement);
        return idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList());
    }

    public List<Integer> modifies(String var) {
        return mtable.getModifies(varTable.getIndexOf(var));
    }

    public boolean uses(int statement, String var) {
        return utable.isUsed(varTable.getIndexOf(var), statement);
    }

    public List<String> uses(int statement) {
        List<Integer> idxList = utable.getUsed(statement);
        return idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList());
    }

    public List<Integer> uses(String var) {
        return utable.getUses(varTable.getIndexOf(var));
    }

    public boolean follows(int s1, int s2) {
        StatementNode statement1 = statTable.getStatement(s1);
        StatementNode statement2 = statTable.getStatement(s2);
        return statement2.getFollows() == statement1;
    }

    public int follows_after(int s1) {
        StatementNode statement1 = statTable.getStatement(s1);
        for (StatementNode statement : statTable.getStatements()) {
            if (statement.getFollows() == statement1) {
                return statement.getLine();
            }
        }
        return -1;
    }

    public int follows_before(int s2) {
        StatementNode statement1 = statTable.getStatement(s2);
        Node before = statement1.getFollows();
        if (before == null) {
            return -1;
        }

        return ((StatementNode)before).getLine();
    }

}
