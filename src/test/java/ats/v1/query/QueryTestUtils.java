package ats.v1.query;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;

import java.util.List;

public class QueryTestUtils {

    public static List<QueryToken> getTokens() { //Select a such that Modifies (a,v) with v.varName=”x”
        return List.of(QueryToken.builder().type(QueryTokenType.SELECT).build(),
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
                QueryToken.builder().type(QueryTokenType.QUOTATION).build()
        );
    }
}
