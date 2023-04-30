package ats.v1.pkb;

import ats.v1.pkb.ast.Ast;
import ats.v1.pkb.ast.AstImpl;
import ats.v1.pkb.ast.nodes.*;
import ats.v1.pkb.proc_table.ProcTable;
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


    public Parser(List<Token> tokens, VarTable varTable, ProcTable procTable) {
        this.tokens = tokens;
        this.varTable = varTable;
        this.procTable = procTable;
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
        checkNextToken(TokenType.LEFT_BRACE);
        List<Node> statements = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        int procedureIdx = procTable.insert(procedure.getLexeme());
        Node procNode = new ProcedureNode(procedureIdx);
        ast.setChildren(procNode, statements);
        return procNode;
    }

    private List<Node> statementList() throws WrongTokenException {
        List<Node> statementList = new ArrayList<>();
        TokenType nextType = lookAhead();
        // TODO: set follows relationship in statement list
        // Stworzyć w AST metode setFollows(current, followed)
        // Pomijając pierwszy statement w statement list, dla każdej przypisać setFollows(statement, statementList.get(index-1))
        while(nextType != TokenType.RIGHT_BRACE && nextType != TokenType.EOF) {
            StatementNode statement = statement();
            statementList.add(statement);
            nextType = lookAhead();
        }

        return statementList;
    }

    private StatementNode statement() throws WrongTokenException {
        Token next = tokens.get(index);
        switch(next.getType()) {
            case WHILE:
                return getWhile();
            case IDENTIFIER:
                return getAssign();
            default:
                throw new WrongTokenException(next.toString());
        }
    }

    private StatementNode getWhile() throws WrongTokenException {
        Token whileToken = checkNextToken(TokenType.WHILE);
        Token condition = checkNextToken(TokenType.IDENTIFIER);
        checkNextToken(TokenType.LEFT_BRACE);
        List<Node> statementList = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);


        StatementNode whileNode = new WhileNode(whileToken.getLine());
        int conditionVarIdx = varTable.insert(condition.getLexeme());
        ast.setChildren(whileNode, statementList);
        ast.setFirstChild(whileNode, new VarNode(conditionVarIdx));
        return whileNode;
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
        return assign;
    }

    private Node getExpression() throws WrongTokenException {
        if (lookAhead(2) == TokenType.SEMICOLON)
            return getFactor();

        Node firstVar = getFactor();
        Token operator = checkNextToken(TokenType.PLUS);
        Node rightExpr = getExpression();

        Node expressionNode = new ExpressionNode(operator.getLexeme());
        ast.addChild(expressionNode, firstVar);
        ast.addChild(expressionNode, rightExpr);
        return expressionNode;
    }

    private Node getFactor() throws WrongTokenException {
        Token factor = checkNextToken(TokenType.NUMBER, TokenType.IDENTIFIER);
        switch(factor.getType()) {
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
        throw new WrongTokenException("\nLINE " + token.getLine() + "\nEXPECTED ONE OF: [ " + allowedTypes + "]\nGOT: " + token.getType());
    }
}

class WrongTokenException extends Exception {
    public WrongTokenException(String msg){
        super(msg);
    }
}
