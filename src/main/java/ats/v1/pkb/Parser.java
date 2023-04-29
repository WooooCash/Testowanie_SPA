package ats.v1.pkb;

import ats.v1.pkb.ast.nodes.*;
import ats.v1.spa_frontend.token.Token;
import ats.v1.spa_frontend.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int index = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;

    }

    public Node parseTokens() {
        Node programRoot = null;
        try {
            programRoot = program();
        } catch (WrongTokenException e) {
            System.err.println("Wrong token");
            e.printStackTrace();
        }
        return programRoot;
    }

    private Node program() throws WrongTokenException {
        List<Node> procedureList = new ArrayList<>();
        TokenType nextType = lookAhead();
        while (nextType != TokenType.EOF) {
            procedureList.add(procedure());
            nextType = lookAhead();
        }

        Node programNode = new ProgramNode();
        programNode.setChildren(procedureList);
        return programNode;
    }

    private Node procedure() throws WrongTokenException {
        checkNextToken(TokenType.PROCEDURE);
        String procName = checkNextToken(TokenType.IDENTIFIER).getLexeme();
        checkNextToken(TokenType.LEFT_BRACE);
        List<Node> statements = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        Node procNode = new ProcedureNode(procName);
        procNode.setChildren(statements);
        return procNode;
    }

    private List<Node> statementList() throws WrongTokenException {
        List<Node> statementList = new ArrayList<>();
        TokenType nextType = lookAhead();
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
        Token condition = checkNextToken(TokenType.IDENTIFIER); // TODO: modify once variable is stored in vartable
        checkNextToken(TokenType.LEFT_BRACE);
        List<Node> statementList = statementList();
        checkNextToken(TokenType.RIGHT_BRACE);

        StatementNode whileNode = new WhileNode(whileToken.getLine());
        whileNode.setChildren(statementList);
        whileNode.getChildren().add(0, new VarNode(0)); // TODO: modify once variable is stored in vartable
        return whileNode;
    }

    private StatementNode getAssign() throws WrongTokenException {
        Token leftVar = checkNextToken(TokenType.IDENTIFIER);// TODO: modify once variable is stored in vartable
        checkNextToken(TokenType.EQUAL);
        Node expression = getExpression();
//        while (tokens.get(index).getType() != TokenType.SEMICOLON) index++;
        checkNextToken(TokenType.SEMICOLON);

        StatementNode assign = new AssignNode(leftVar.getLine());
        assign.getChildren().add(new VarNode(1));// TODO: modify once variable is stored in vartable
        assign.getChildren().add(expression);
        return assign;
    }

    private Node getExpression() throws WrongTokenException {
        if (lookAhead(2) == TokenType.SEMICOLON)
            return getFactor();

        Node firstVar = getFactor();
        checkNextToken(TokenType.PLUS);
        Node rightExpr = getExpression();

        Node expressionNode = new ExpressionNode('+');
        expressionNode.getChildren().add(firstVar);
        expressionNode.getChildren().add(rightExpr);
        return expressionNode;
    }

    private Node getFactor() throws WrongTokenException {
        Token factor = checkNextToken(TokenType.NUMBER, TokenType.IDENTIFIER);
        switch(factor.getType()) {
            case NUMBER:
                return new ConstNode(factor.getValue());
            case IDENTIFIER:
                return new VarNode(1);
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
        throw new WrongTokenException("\nLINE " + token.getLine() + "\nEXPECTED ONE OF: [ " + allowedTypes + "]\nGOT: " + token.toString());
    }



}

class WrongTokenException extends Exception {
    public WrongTokenException(String msg){
        super(msg);
    }
}
