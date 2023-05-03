package ats.v1.spa_frontend.scanner;

import ats.v1.common.Token;
import ats.v1.spa_frontend.token.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ats.v1.spa_frontend.token.TokenType.*;

@Slf4j
@RequiredArgsConstructor
public class Scanner {

    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("procedure", PROCEDURE);
        keywords.put("while", WHILE);
    } //TODO wynieść do zewnętrznej klasy

    private final String programSource;
    private final List<Token<TokenType>> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;


    public List<Token<TokenType>> scanTokens() {
        while (!atEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token<>(EOF, "", -1, line));
        return tokens;
    }

    private boolean atEnd() {
        return current >= programSource.length();
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case '+': addToken(PLUS); break;
            case '-': addToken(MINUS); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
            case ';': addToken(SEMICOLON); break;
            case '=': addToken(EQUAL); break;
            case ' ': case '\r': case '\t': break;
            case '\n': line++; break;

            default:
                if (isDigit(c)) {
                    number();
                    break;
                }
                if (isAlpha(c)) {
                    identifier();
                    break;
                }
                //log.error("[Scanner] Unrecognized character {} on line {}", c, line); //todo ogarnąć slf4j
                System.err.printf("[Scanner] Unrecognized character %c on line %d\n", c, line);
                break;
        }
    }

    private void number() {
        while (isDigit(lookAhead())) advance();
        addToken(NUMBER, Integer.parseInt(programSource.substring(start, current)));
    }

    private void identifier() {
        while(isAlphaNumeric(lookAhead())){
            advance();
        }
        String lexeme = programSource.substring(start, current);
        TokenType type = keywords.get(lexeme);
        if(type == null) {
            type = IDENTIFIER;
        }
        addToken(type);
    }

    private boolean match(char expected) {
        if (atEnd()) return false;
        if (programSource.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char lookAhead() {
        return atEnd() ? '\0' : programSource.charAt(current);
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private char advance() {
        return programSource.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, -1);
    }

    private void addToken(TokenType type, int value) {
        String lexeme = programSource.substring(start, current);
        tokens.add(new Token<TokenType>(type, lexeme, value, line));
    }

}