package ats.v1.query.parser;

import ats.v1.common.QueryUtils;
import ats.v1.common.Parser;
import ats.v1.query.token.QueryToken;
import ats.v1.spa_frontend.token.Token;
import ats.v1.query.token.QueryTokenValue;
import ats.v1.query.token.QueryTokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryParser implements Parser<QueryToken> {

    private final QueryTokenValue queryTokenValue = new QueryTokenValue();
    private final List<QueryToken> tokens = new ArrayList<>();

    @Override
    public List<QueryToken> parse(String query) {
        Scanner scanner = new Scanner(query);
        String currentArg;
        while (scanner.hasNext()) {
            currentArg = scanner.next().toLowerCase();
            QueryTokenType currentToken = queryTokenValue.getTokenType(currentArg);
            if (currentToken != null) {
                QueryToken token = QueryToken.builder().type(currentToken).build();
                tokens.add(token);
            } else { //TODO do poprawy mechanizm varName...
                for (int i = 0; i < currentArg.length(); i++) {
                    char currentChar = currentArg.charAt(i);
                    QueryTokenType queryTokenType = queryTokenValue.getTokenType(String.valueOf(currentChar));
                    addIfExists(queryTokenType);
                    if (QueryUtils.isDigit(currentChar)) {
                        String number = String.valueOf(currentChar);
                        while (currentArg.length() > i + 1 && QueryUtils.isDigit(currentArg.charAt(i + 1))) {
                            number += currentArg.charAt(i + 1);
                            i++;
                        }
                        int valueNumber = Integer.parseInt(number);
                        QueryToken token = QueryToken.builder().type(QueryTokenType.NUMBER).value(valueNumber).build();
                        tokens.add(token);
                    }
                    if (QueryUtils.isAlpha(currentChar)) {
                        String letter = String.valueOf(currentChar);
                        while (currentArg.length() > i + 1 && (QueryUtils.isAlpha(currentArg.charAt(i + 1)) || QueryUtils.isDigit(currentArg.charAt(i + 1)))) {
                            letter += currentArg.charAt(i + 1);
                            i++;
                        }
                        QueryToken token = QueryToken.builder().type(QueryTokenType.LEXEME).lexeme(letter).build();
                        tokens.add(token);
                    }
                }


            }


        }
        return tokens;
    }

    private void addIfExists(QueryTokenType queryTokenType) {
        if (queryTokenType != null) {
            QueryToken token = QueryToken.builder().type(queryTokenType).build();
            tokens.add(token);
        }
    }


}
