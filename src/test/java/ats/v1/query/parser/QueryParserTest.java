package ats.v1.query.parser;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class QueryParserTest {

    @Test
    void shouldParseSimpleQueryCorrectly() {
        String query = "Select s1 such that Follows (s1, s2)";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(null, query);
        assertThat(tokens).isNotNull().hasSize(11);
        assertThat(tokens.get(0).getType()).isEqualTo(QueryTokenType.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(QueryTokenType.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(2).getType()).isEqualTo(QueryTokenType.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(QueryTokenType.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(QueryTokenType.FOLLOWS);
        assertThat(tokens.get(5).getType()).isEqualTo(QueryTokenType.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(QueryTokenType.LEXEME);
        assertThat(tokens.get(6).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(7).getType()).isEqualTo(QueryTokenType.COMMA);
        assertThat(tokens.get(8).getType()).isEqualTo(QueryTokenType.LEXEME);
        assertThat(tokens.get(8).getLexeme()).isEqualTo("s2");
        assertThat(tokens.get(9).getType()).isEqualTo(QueryTokenType.RIGHT_PAREN);
    }

    @Test
    void shouldParseQueryCorrectly() {
        String query = "Select a such that Follows (\"20\", a)";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(null, query);
        assertThat(tokens).isNotNull().hasSize(13);
        assertThat(tokens.get(0).getType()).isEqualTo(QueryTokenType.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(QueryTokenType.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(2).getType()).isEqualTo(QueryTokenType.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(QueryTokenType.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(QueryTokenType.FOLLOWS);
        assertThat(tokens.get(5).getType()).isEqualTo(QueryTokenType.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(QueryTokenType.QUOTATION2);
        assertThat(tokens.get(7).getType()).isEqualTo(QueryTokenType.NUMBER);
        assertThat(tokens.get(7).getValue()).isEqualTo(20);
        assertThat(tokens.get(8).getType()).isEqualTo(QueryTokenType.QUOTATION2);
        assertThat(tokens.get(9).getType()).isEqualTo(QueryTokenType.COMMA);
        assertThat(tokens.get(10).getType()).isEqualTo(QueryTokenType.LEXEME);
        assertThat(tokens.get(10).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(11).getType()).isEqualTo(QueryTokenType.RIGHT_PAREN);
    }

    @Test
    void shouldParseComplexQueryCorrectly() {
        String declare = "prog_line a; while v;\n";
        String query = "Select a such that Modifies (a,v) with v.varName=”x”";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(declare, query);
        assertThat(tokens).isNotNull();
        assertThat(tokens).hasSize(25);
    }

}