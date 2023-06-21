package ats.v1.pkb;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.AstImpl;
import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.proc_table.ProcTable;
import ats.v1.pkb.statement_table.StatementTable;
import ats.v1.pkb.var_table.VarTable;
import ats.v1.spa_frontend.token.Token;
import ats.v1.spa_frontend.token.TokenType;

import java.util.ArrayList;
import java.util.List;
public class Parser {
    private final List<Token> tokens;
    private int index = 0;

    private Ast ast;
    private VarTable varTable;
    private ProcTable procTable;
    private StatementTable statTable;


    public Parser(List<Token> tokens, VarTable varTable, ProcTable procTable, StatementTable statTable) {
        this.tokens = tokens;
        this.varTable = varTable;
        this.procTable = procTable;
        this.statTable = statTable;
    }

    public Ast parseTokens() {
        ast = new AstImpl(new ProgramNode());
        try {
            program();
        } catch (WrongTokenException e) {
            e.printStackTrace();
        }
        return ast;
    }

    private void program() throws WrongTokenException {
        Node programNode = ast.getRoot();
        TokenType nextType = lookAhead();
        while (nextType != TokenType.EOF) {
            ast.addChild(programNode, procedure());
            nextType = lookAhead();
        }
    }

    private Node procedure() throws WrongTokenException {
        checkNextToken(TokenType.PROCEDURE);
        Token procedure = checkNextToken(TokenType.IDENTIFIER);
        int procedureIdx = procTable.insert(procedure.getLexeme());
        Node procNode = new ProcedureNode(procedureIdx);
        checkNextToken(TokenType.LEFT_BRACE);
        Node statementList = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        ast.addChild(procNode, statementList);
        return procNode;
    }

    private StatementListNode statementList() throws WrongTokenException {
        List<Node> statementList = new ArrayList<>();
        TokenType nextType = lookAhead();
        StatementNode previous = null;

        while (nextType != TokenType.RIGHT_BRACE && nextType != TokenType.EOF) {
            StatementNode statement = statement();
            if (previous != null) {
                statement.setFollows(previous);
            }
            statementList.add(statement);
            nextType = lookAhead();
            previous = statement;
        }
        StatementListNode statementListNode = new StatementListNode();
        ast.setChildren(statementListNode, statementList);
        return statementListNode;
    }

    private StatementNode statement() throws WrongTokenException {
        Token next = tokens.get(index);
        switch (next.getType()) {
            case WHILE:
                return getWhile();
            case IF:
                return getIf();
            case IDENTIFIER:
                return getAssign();
            case CALL:
                return getCall();
            default:
                throw new WrongTokenException(next.toString());
        }
    }

    private StatementNode getWhile() throws WrongTokenException {
        Token whileToken = checkNextToken(TokenType.WHILE);
        Token condition = checkNextToken(TokenType.IDENTIFIER);
        checkNextToken(TokenType.LEFT_BRACE);
        Node statementList = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        StatementNode whileNode = new WhileNode(whileToken.getLine());
        int conditionVarIdx = varTable.insert(condition.getLexeme());
        ast.setFirstChild(whileNode, new VarNode(conditionVarIdx));
        ast.addChild(whileNode, statementList);

        statTable.addStatement(whileNode);
        return whileNode;
    }

    private StatementNode getIf() throws WrongTokenException {
        Token ifToken = checkNextToken(TokenType.IF);
        Token condition = checkNextToken(TokenType.IDENTIFIER);
        checkNextToken(TokenType.THEN);
        checkNextToken(TokenType.LEFT_BRACE);
        Node statementList = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        StatementNode ifNode = new IfNode(ifToken.getLine());
        int conditionVarIdx = varTable.insert(condition.getLexeme());
        ast.setFirstChild(ifNode, new VarNode(conditionVarIdx));
        ast.addChild(ifNode, statementList);

        if (lookAhead() == TokenType.ELSE) {
            checkNextToken(TokenType.ELSE);
            checkNextToken(TokenType.LEFT_BRACE);
            Node elseStatementList = statementList();
            checkNextToken(TokenType.RIGHT_BRACE);
            ast.addChild(ifNode, elseStatementList);
        }

        statTable.addStatement(ifNode);
        return ifNode;
    }

    private StatementNode getCall() throws WrongTokenException {
        checkNextToken(TokenType.CALL);
        Token procedureName = checkNextToken(TokenType.IDENTIFIER);
        checkNextToken(TokenType.SEMICOLON);

        int procedureIdx = procTable.insert(procedureName.getLexeme());
        CallNode callNode = new CallNode(procedureName.getLine(), procedureIdx);
        statTable.addStatement(callNode);
        return callNode;
    }

    private StatementNode getAssign() throws WrongTokenException {
        Token leftVar = checkNextToken(TokenType.IDENTIFIER);
        checkNextToken(TokenType.EQUAL);
        Node expression = getExpression();
        checkNextToken(TokenType.SEMICOLON);

        StatementNode assign = new AssignNode(leftVar.getLine());
        int leftVarIdx = varTable.insert(leftVar.getLexeme());
        ast.setFirstChild(assign, new VarNode(leftVarIdx));
        ast.addChild(assign, expression);

        statTable.addStatement(assign);
        return assign;
    }

    private Node getExpression() throws WrongTokenException {
        if (lookAhead(2) == TokenType.SEMICOLON) {
            return getFactor();
        }

        Node firstVar = getFactor();
        Token operator = checkNextToken(TokenType.PLUS, TokenType.STAR, TokenType.MINUS);
        Node rightExpr = getExpression();


        Node expressionNode = new ExpressionNode(operator.getLexeme());
        ast.addChild(expressionNode, firstVar);
        ast.addChild(expressionNode, rightExpr);
        return expressionNode;
    }

    private Node getFactor() throws WrongTokenException {
        Token factor = checkNextToken(TokenType.NUMBER, TokenType.IDENTIFIER);
        switch (factor.getType()) {
            case NUMBER:
                return new ConstNode(factor.getValue());
            case IDENTIFIER:
                int factorVarIdx = varTable.insert(factor.getLexeme());
                return new VarNode(factorVarIdx);
            default:
                throw new WrongTokenException("Error parsing factor " + factor.getLexeme() + " at line " + factor.getLine());
        }
    }

    private TokenType lookAhead() {
        return tokens.get(index).getType();
    }

    private TokenType lookAhead(int dist) {
        return tokens.get(index + dist - 1).getType();
    }

    private Token checkNextToken(TokenType... types) throws WrongTokenException {
        Token token = tokens.get(index++);
        StringBuilder allowedTypes = new StringBuilder();
        for (TokenType type : types) {
            if (token.getType() == type)
                return token;
            allowedTypes.append(type.toString()).append(" ");
        }
        throw new WrongTokenException("\nLINE " + token.getLine() + "\n" +
                "EXPECTED ONE OF: [ " + allowedTypes + "]\n" +
                "GOT: " + token.getType() + " (" + token.getLexeme()+")");
    }
}

class WrongTokenException extends Exception {
    public WrongTokenException(String msg) {
        super(msg);
    }
}
