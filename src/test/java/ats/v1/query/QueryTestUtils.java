package ats.v1.query;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenValue;

import java.util.List;

public class QueryTestUtils {

    public static List<QueryToken> getTokens() { //Select a such that Modifies (a,v) with v.varName=”x”
        return List.of(QueryToken.builder().type(QueryTokenValue.SELECT).build(),
                QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenValue.SUCH).build(),
                QueryToken.builder().type(QueryTokenValue.THAT).build(),
                QueryToken.builder().type(QueryTokenValue.MODIFIES).build(),
                QueryToken.builder().type(QueryTokenValue.LEFT_PAREN).build(),
                QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("a").build(),
                QueryToken.builder().type(QueryTokenValue.COMMA).build(),
                QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenValue.RIGHT_PAREN).build(),
                QueryToken.builder().type(QueryTokenValue.WITH).build(),
                QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("v").build(),
                QueryToken.builder().type(QueryTokenValue.DOT).build(),
                QueryToken.builder().type(QueryTokenValue.VARNAME).build(),
                QueryToken.builder().type(QueryTokenValue.EQUALS).build(),
                QueryToken.builder().type(QueryTokenValue.QUOTATION).build(),
                QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("x").build(),
                QueryToken.builder().type(QueryTokenValue.QUOTATION).build()
        );
    }

    public static List<QueryToken> getDeclarationTokens() {
        return List.of(
            QueryToken.builder().type(QueryTokenValue.STMT).build(),
            QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("s1").build(),
            QueryToken.builder().type(QueryTokenValue.COMMA).build(),
            QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("s2").build(),
            QueryToken.builder().type(QueryTokenValue.SEMICOLON).build(),
            QueryToken.builder().type(QueryTokenValue.ASSIGN).build(),
            QueryToken.builder().type(QueryTokenValue.LEXEME).lexeme("a").build(),
            QueryToken.builder().type(QueryTokenValue.SEMICOLON).build(),
            QueryToken.builder().type(QueryTokenValue.SELECT).build()
        );
    }
}
