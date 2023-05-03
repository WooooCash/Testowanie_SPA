package ats.v1.query.parser;

import ats.v1.common.QueryUtils;
import ats.v1.common.Reader;
import ats.v1.common.Token;
import ats.v1.query.token.QueryTokenValue;
import ats.v1.query.token.QueryToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryReader implements Reader<Token<QueryToken>> {

    private final QueryTokenValue queryTokenValue = new QueryTokenValue();
    private final List<Token<QueryToken>> tokens = new ArrayList<>();

    @Override
    public List<Token<QueryToken>> parse(String query) {
        Scanner scanner = new Scanner(query);
        String currentArg;
        while (scanner.hasNext()) {
            currentArg = scanner.next();
            QueryToken currentToken = queryTokenValue.getTokenType(currentArg);
            if (currentToken != null) {
                Token token = Token.builder().type(currentToken).build();
                tokens.add(token);
            } else { //TODO do poprawy mechanizm varName...
                for (int i = 0; i < currentArg.length(); i++) {
                    char currentChar = currentArg.charAt(i);
                    QueryToken queryToken = queryTokenValue.getTokenType(String.valueOf(currentChar));
                    addIfExists(queryToken);
                    if (QueryUtils.isDigit(currentChar)) {
                        String number = String.valueOf(currentChar);
                        while (isDigit(currentArg.charAt(i + 1))) {
                            number += currentArg.charAt(i + 1);
                            i++;
                        }
                        int valueNumber = Integer.parseInt(number);
                        Token token = Token.builder().type(QueryToken.NUMBER).value(valueNumber).build();
                        tokens.add(token);
                    }
                    if (QueryUtils.isAlpha(currentChar)) {
                        String letter = String.valueOf(currentChar);
                        while (currentArg.length() > i + 1 && (QueryUtils.isAlpha(currentArg.charAt(i + 1)) || QueryUtils.isDigit(currentArg.charAt(i + 1)))) {
                            letter += currentArg.charAt(i + 1);
                            i++;
                        }
                        Token token = Token.builder().type(QueryToken.LEXEME).lexeme(letter).build();
                        tokens.add(token);
                    }
                }


            }


        }
        return tokens;
    }

    private void addIfExists(QueryToken queryToken) {
        if (queryToken != null) {
            Token token = Token.builder().type(queryToken).build();
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
