package ats.v1.pkb;

import ats.v1.pkb.ast.nodes.AssignNode;
import ats.v1.pkb.ast.nodes.Node;
import ats.v1.pkb.ast.nodes.StatementNode;
import ats.v1.pkb.ast.nodes.WhileNode;
import ats.v1.pkb.modifies_table.ModifiesTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.uses_table.UsesTable;
import ats.v1.pkb.var_table.VarTable;
import ats.v1.spa_frontend.token.TokenType;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ats.v1.spa_frontend.token.TokenType.PROCEDURE;
import static ats.v1.spa_frontend.token.TokenType.WHILE;

@AllArgsConstructor
public class PKB {
    private ModifiesTable mtable;
    private UsesTable utable;
    private VarTable varTable;
    private StatementTable statTable;

    private static final Map<String, Class> statementMapping;
    static {
        statementMapping = new HashMap<>();
        statementMapping.put("a", AssignNode.class);
        statementMapping.put("w", WhileNode.class);
    } //TODO wynieść do zewnętrznej klasy

    public boolean modifies(int statement, String var) {
        return mtable.isModified(varTable.getIndexOf(var), statement);
    }

    public List<String> modifies(int statement) {
        List<Integer> idxList = mtable.getModified(statement);
        return idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList());
    }

    public List<Integer> modifies(String var, String type) {
        List<Integer> allStatements = mtable.getModifies(varTable.getIndexOf(var));
        return filterStatements(allStatements, type);
    }

    public boolean uses(int statement, String var) {
        return utable.isUsed(varTable.getIndexOf(var), statement);
    }

    public List<String> uses(int statement) {
        List<Integer> idxList = utable.getUsed(statement);
        return idxList.stream().map(i -> varTable.getName(i)).collect(Collectors.toList());
    }

    public List<Integer> uses(String var, String type) {
        List<Integer> allStatements = utable.getUses(varTable.getIndexOf(var));
        return filterStatements(allStatements, type);
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


    public List<Integer> filterStatements(List<Integer> all, String type) {
        if (type == null) {
            return all;
        }
        return all
                .stream()
                .filter(stmt -> statTable.getStatement(stmt).getClass() == statementMapping.get(type))
                .collect(Collectors.toList());
    }
}
