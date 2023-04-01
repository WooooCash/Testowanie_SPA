package ats.v1.spa_frontend.token;

import java.util.HashMap;
import java.util.Map;

public class TokenValue {

    //todo zmienić na static?
    private final Map<String, TokenType> values = new HashMap<>();

    public TokenValue() {
        initValues();
    }

    private void initValues() {
        values.put("(", TokenType.LEFT_PAREN);
        values.put(")", TokenType.RIGHT_PAREN);
        values.put("{", TokenType.LEFT_BRACE);
        values.put("}", TokenType.RIGHT_BRACE);
        values.put("+", TokenType.PLUS);
        values.put("-", TokenType.MINUS);
        values.put("*", TokenType.STAR);
        values.put("/", TokenType.SLASH);
        values.put(";", TokenType.SEMICOLON);
        values.put("=", TokenType.EQUAL);
        values.put("\n", TokenType.NEW_LINE);
    }

    public TokenType getTokenType(final String value) {
        return values.get(value);
    }

}
