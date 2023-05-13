package ats.v1.spa_frontend.token;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Token {

    final TokenType type;
    final String lexeme;
    final int value;
    final int line;

    public String toString() {
//        return line + ": " + type + " " + lexeme + " " + (value != -1 ? value : "");
        return String.format("%2d %11s %9s %2s", line, type, lexeme, (value != -1 ? Integer.toString(value) : ""));
    }

}
