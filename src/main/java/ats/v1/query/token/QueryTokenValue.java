package ats.v1.query.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum QueryTokenValue {
    SELECT("SELECT", "select", QueryTokenType.ESSENTIAL),
    SUCH("SUCH", "such", QueryTokenType.ESSENTIAL),
    THAT("THAT", "that", QueryTokenType.ESSENTIAL),
    WITH("WITH", "with", QueryTokenType.ESSENTIAL),
    PATTERN("PATTERN", "pattern", QueryTokenType.ESSENTIAL),
    FOLLOWS("FOLLOWS", "follows", QueryTokenType.SUCH_THAT),
    FOLLOWS_PLUS("FOLLOWS_PLUS", "follows*", QueryTokenType.SUCH_THAT),
    PARENT("PARENT", "parent", QueryTokenType.SUCH_THAT),
    PARENT_PLUS("PARENT_PLUS", "parent*", QueryTokenType.SUCH_THAT),
    MODIFIES("MODIFIES", "modifies", QueryTokenType.SUCH_THAT),
    MODIFIES_PLUS("MODIFIES_PLUS", "modifies*", QueryTokenType.SUCH_THAT),
    DOT("DOT", ".", QueryTokenType.OTHER),
    AND("AND", "and", QueryTokenType.OTHER),
    QUOTATION("QUOTATION", "\"", QueryTokenType.OTHER),
    QUOTATION2("QUOTATION", "”", QueryTokenType.OTHER),
    QUOTATION3("QUOTATION", "'", QueryTokenType.OTHER),
    VARNAME("VARNAME", "varname", QueryTokenType.OTHER),
    EQUALS("EQUALS", "=", QueryTokenType.OTHER),
    NUMBER("NUMBER", "number", QueryTokenType.OTHER),
    LEXEME("LEXEME", "lexeme", QueryTokenType.OTHER),
    LEFT_PAREN("LEFT_PAREN", "(", QueryTokenType.OTHER),
    RIGHT_PAREN("RIGHT_PAREN", ")", QueryTokenType.OTHER),
    MINORITY("MINORITY", "<", QueryTokenType.OTHER),
    MAJORITY("MAJORITY", ">", QueryTokenType.OTHER),
    COMMA("COMMA", ",", QueryTokenType.OTHER),
    EOL("EOL", "", QueryTokenType.OTHER),
    SEMICOLON("SEMICOLON", ";", QueryTokenType.OTHER),
    STMT("STMT", "stmt", QueryTokenType.DECLARATION),
    ASSIGN("ASSIGN", "assign", QueryTokenType.DECLARATION),
    WHILE("WHILE", "while", QueryTokenType.DECLARATION),
    PROCEDURE("PROCEDURE", "procedure", QueryTokenType.DECLARATION),
    PROGLINE("PROGLINE", "prog_line", QueryTokenType.DECLARATION),
    IF("IF", "if", QueryTokenType.DECLARATION),
    CALL("CALL", "call", QueryTokenType.DECLARATION),
    VARIABLE("VARIABLE", "variable", QueryTokenType.DECLARATION),
    ;

    @Getter
    private final String value;

    @Getter
    private final String queryValue;

    @Getter
    private final QueryTokenType type;

}
