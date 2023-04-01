package ats.v1.spa_frontend.token;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class TokenValueProvider {

    private static final String NEW_LINE = "\n";
    private static final List<String> emptyChars = List.of(" ", "\r", "\t");

    private final TokenValue tokenValue = new TokenValue();

    public TokenType provide(final String value) {
        TokenType type;
        type = tokenValue.getTokenType(value);
        if(type != null) {
            return type;
        }
        if(emptyChars.contains(value)) {
            return null;
        }
        if(value.equals(NEW_LINE)) {
            return TokenType.NEW_LINE;
        }
        //TODO
        throw new RuntimeException("Error - Incorrect char!");
    }

}
