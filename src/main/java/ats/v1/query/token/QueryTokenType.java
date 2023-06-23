package ats.v1.query.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum QueryTokenType {
    SELECT("SELECT", "select", QueryTokenGroup.ESSENTIAL),
    SUCH("SUCH", "such", QueryTokenGroup.ESSENTIAL),
    THAT("THAT", "that", QueryTokenGroup.ESSENTIAL),
    WITH("WITH", "with", QueryTokenGroup.ESSENTIAL),
    PATTERN("PATTERN", "pattern", QueryTokenGroup.ESSENTIAL),
    FOLLOWS("FOLLOWS", "follows", QueryTokenGroup.SUCH_THAT),
    FOLLOWS_PLUS("FOLLOWS_PLUS", "follows*", QueryTokenGroup.SUCH_THAT),
    PARENT("PARENT", "parent", QueryTokenGroup.SUCH_THAT),
    PARENT_PLUS("PARENT_PLUS", "parent*", QueryTokenGroup.SUCH_THAT),
    MODIFIES("MODIFIES", "modifies", QueryTokenGroup.SUCH_THAT),
    MODIFIES_PLUS("MODIFIES_PLUS", "modifies*", QueryTokenGroup.SUCH_THAT),
    AFFECTS("AFFECTS", "affects", QueryTokenGroup.SUCH_THAT),
    DOT("DOT", ".", QueryTokenGroup.OTHER),
    AND("AND", "and", QueryTokenGroup.OTHER),
    QUOTATION("QUOTATION", "”", QueryTokenGroup.OTHER),
    QUOTATION2("QUOTATION", "\"", QueryTokenGroup.OTHER),
    QUOTATION3("QUOTATION", "'", QueryTokenGroup.OTHER),
    WILDCARD("WILDCARD", "_", QueryTokenGroup.OTHER),
    VARNAME("VARNAME", "varname", QueryTokenGroup.OTHER),
    EQUALS("EQUALS", "=", QueryTokenGroup.OTHER),
    NUMBER("NUMBER", "number", QueryTokenGroup.OTHER),
    LEXEME("LEXEME", "lexeme", QueryTokenGroup.OTHER),
    LEFT_PAREN("LEFT_PAREN", "(", QueryTokenGroup.OTHER),
    RIGHT_PAREN("RIGHT_PAREN", ")", QueryTokenGroup.OTHER),
    MINORITY("MINORITY", "<", QueryTokenGroup.OTHER),
    MAJORITY("MAJORITY", ">", QueryTokenGroup.OTHER),
    COMMA("COMMA", ",", QueryTokenGroup.OTHER),
    EOL("EOL", "", QueryTokenGroup.OTHER),
    SEMICOLON("SEMICOLON", ";", QueryTokenGroup.OTHER),
    STMT("STMT", "stmt", QueryTokenGroup.DECLARATION),
    ASSIGN("ASSIGN", "assign", QueryTokenGroup.DECLARATION),
    WHILE("WHILE", "while", QueryTokenGroup.DECLARATION),
    PROCEDURE("PROCEDURE", "procedure", QueryTokenGroup.DECLARATION),
    PROGLINE("PROGLINE", "prog_line", QueryTokenGroup.DECLARATION),
    IF("IF", "if", QueryTokenGroup.DECLARATION),
    CALL("CALL", "call", QueryTokenGroup.DECLARATION),
    VARIABLE("VARIABLE", "variable", QueryTokenGroup.DECLARATION),
    ;

    @Getter
    private final String value;

    @Getter
    private final String queryValue;

    @Getter
    private final QueryTokenGroup group;

}
