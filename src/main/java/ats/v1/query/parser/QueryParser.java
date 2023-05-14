package ats.v1.query.parser;

import ats.v1.common.Parser;
import ats.v1.common.QueryUtils;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.token.QueryTokenValue;

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
                addIfExists(currentToken);
            } else {
                checkCharByChar(currentArg);
            }
        }
        return tokens;
    }

    private void addIfExists(final QueryTokenType currentToken) {
        QueryToken token = QueryToken.builder().type(currentToken).build();
        tokens.add(token);
    }

    private void checkCharByChar(final String currentArg) {
        for (int i = 0; i < currentArg.length(); i++) {
            char currentChar = currentArg.charAt(i);
            QueryTokenType queryTokenType = queryTokenValue.getTokenType(String.valueOf(currentChar));
            if (queryTokenType != null) {
                QueryToken token = QueryToken.builder().type(queryTokenType).build();
                tokens.add(token);
            }
            else{
                if (QueryUtils.isDigit(currentChar)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentChar);
                    while (QueryUtils.isDigit(currentArg.charAt(i + 1))) {
                        sb.append(currentArg.charAt(++i));
                    }
                    int valueNumber = Integer.parseInt(sb.toString());
                    QueryToken token = QueryToken.builder().type(QueryTokenType.NUMBER).value(valueNumber).build();
                    tokens.add(token);
                }
                if (QueryUtils.isAlpha(currentChar)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentChar);
                    while (currentArg.length() > i + 1 && (QueryUtils.isAlpha(currentArg.charAt(i + 1))
                            || QueryUtils.isDigit(currentArg.charAt(i + 1)))) {
                        sb.append(currentArg.charAt(++i));
                    }
                    QueryToken token = QueryToken.builder().type(QueryTokenType.LEXEME).lexeme(sb.toString()).build();
                    tokens.add(token);
                }
            }
        }
    }

}
