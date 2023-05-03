package ats.v1.query.parser;

import ats.v1.common.QueryUtils;
import ats.v1.common.Reader;
import ats.v1.common.Token;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.TokenT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryReader implements Reader<Token<TokenT>> {

    private final QueryToken queryToken = new QueryToken();
    private final List<Token<TokenT>> tokens = new ArrayList<>();

    @Override
    public List<Token<TokenT>> parse(String query) {
        Scanner scanner = new Scanner(query);
        String currentArg;
        while (scanner.hasNext()) {
            currentArg = scanner.next();
            TokenT currentToken = queryToken.getTokenType(currentArg);
            if (currentToken != null) {
                Token token = Token.builder().type(currentToken).build();
                tokens.add(token);
            } else { //TODO do poprawy mechanizm varName...
                for (int i = 0; i < currentArg.length(); i++) {
                    char currentChar = currentArg.charAt(i);
                    TokenT tokenT = queryToken.getTokenType(String.valueOf(currentChar));
                    addIfExists(tokenT);
                    if (QueryUtils.isDigit(currentChar)) {
                        String number = String.valueOf(currentChar);
                        while (isDigit(currentArg.charAt(i + 1))) {
                            number += currentArg.charAt(i + 1);
                            i++;
                        }
                        int valueNumber = Integer.parseInt(number);
                        Token token = Token.builder().type(TokenT.NUMBER).value(valueNumber).build();
                        tokens.add(token);
                    }
                    if (QueryUtils.isAlpha(currentChar)) {
                        String letter = String.valueOf(currentChar);
                        while (currentArg.length() > i + 1 && (QueryUtils.isAlpha(currentArg.charAt(i + 1)) || QueryUtils.isDigit(currentArg.charAt(i + 1)))) {
                            letter += currentArg.charAt(i + 1);
                            i++;
                        }
                        Token token = Token.builder().type(TokenT.LEXEME).lexeme(letter).build();
                        tokens.add(token);
                    }
                }


            }


        }
        return tokens;
    }

    private void addIfExists(TokenT tokenT) {
        if (tokenT != null) {
            Token token = Token.builder().type(tokenT).build();
            tokens.add(token);
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


}
