package ats.v1.query;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;

import java.util.List;

public class QueryTestUtils {

    public static List<QueryToken> getTokens() { //assign a; while v; Select a such that Modifies (a,v) with v.varName=”x” and v.value = 1
        return List.of(
                QueryToken.builder().type(QueryTokenType.ASSIGN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.WHILE).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.SELECT).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenType.SUCH).build(),
                QueryToken.builder().type(QueryTokenType.THAT).build(),
                QueryToken.builder().type(QueryTokenType.MODIFIES).build(),
                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.WITH).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.DOT).build(),
                QueryToken.builder().type(QueryTokenType.VARNAME).build(),
                QueryToken.builder().type(QueryTokenType.EQUALS).build(),
                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("x").build(),
                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
                QueryToken.builder().type(QueryTokenType.AND).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.DOT).build(),
                QueryToken.builder().type(QueryTokenType.VALUE).build(),
                QueryToken.builder().type(QueryTokenType.EQUALS).build(),
                QueryToken.builder().type(QueryTokenType.NUMBER).value(1).build(),
                QueryToken.builder().type(QueryTokenType.AND).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.DOT).build(),
                QueryToken.builder().type(QueryTokenType.VARNAME).build(),
                QueryToken.builder().type(QueryTokenType.HASH).build(),
                QueryToken.builder().type(QueryTokenType.EQUALS).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenType.DOT).build(),
                QueryToken.builder().type(QueryTokenType.STMT).build(),
                QueryToken.builder().type(QueryTokenType.HASH).build(),
                QueryToken.builder().type(QueryTokenType.EOL).build()
        );
    }

    public static List<QueryToken> getDeclarationTokens() {
        return List.of(
                QueryToken.builder().type(QueryTokenType.STMT).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("s1").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("s2").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.ASSIGN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.SELECT).build(),
                QueryToken.builder().type(QueryTokenType.EOL).build()
        );
    }

    public static List<QueryToken> getWKurweMocneTokens() { //assign a1, a2; while w1, w2;
        return List.of(                                     //Select a1 pattern a1 (“x”, _) and a2 (“x”,_”x”_) such that Affects (a1, a2) and Parent* (w2, a2) and Parent* (w1, w2)
                QueryToken.builder().type(QueryTokenType.ASSIGN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a1").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a2").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.WHILE).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("w1").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("w2").build(),
                QueryToken.builder().type(QueryTokenType.SEMICOLON).build(),
                QueryToken.builder().type(QueryTokenType.SELECT).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a1").build(),
//                QueryToken.builder().type(QueryTokenType.PATTERN).build(),
//                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a1").build(),
//                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("x").build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.COMMA).build(),
//                QueryToken.builder().type(QueryTokenType.WILDCARD).build(),
//                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
//                QueryToken.builder().type(QueryTokenType.AND).build(),
//                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a2").build(),
//                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("x").build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.COMMA).build(),
//                QueryToken.builder().type(QueryTokenType.WILDCARD).build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("x").build(),
//                QueryToken.builder().type(QueryTokenType.QUOTATION).build(),
//                QueryToken.builder().type(QueryTokenType.WILDCARD).build(),
//                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.SUCH).build(),
                QueryToken.builder().type(QueryTokenType.THAT).build(),
                QueryToken.builder().type(QueryTokenType.AFFECTS).build(),
                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a1").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a2").build(),
                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.AND).build(),
                QueryToken.builder().type(QueryTokenType.PARENT_PLUS).build(),
                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("w2").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("a2").build(),
                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.AND).build(),
                QueryToken.builder().type(QueryTokenType.PARENT_PLUS).build(),
                QueryToken.builder().type(QueryTokenType.LEFT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("w1").build(),
                QueryToken.builder().type(QueryTokenType.COMMA).build(),
                QueryToken.builder().type(QueryTokenType.LEXEME).lexeme("w2").build(),
                QueryToken.builder().type(QueryTokenType.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenType.EOL).build()
        );
    }
}
