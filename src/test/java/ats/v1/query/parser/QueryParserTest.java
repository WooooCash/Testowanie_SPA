package ats.v1.query.parser;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class QueryParserTest {

    @Test
    void shouldParseSimpleQueryCorrectly() {
        String query = "Select s1 such that Follows (s1, s2)";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(null, query);
        assertThat(tokens).isNotNull().hasSize(10);
        assertThat(tokens.get(0).getType()).isEqualTo(QueryTokenValue.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(QueryTokenValue.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(2).getType()).isEqualTo(QueryTokenValue.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(QueryTokenValue.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(QueryTokenValue.FOLLOWS);
        assertThat(tokens.get(5).getType()).isEqualTo(QueryTokenValue.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(QueryTokenValue.LEXEME);
        assertThat(tokens.get(6).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(7).getType()).isEqualTo(QueryTokenValue.COMMA);
        assertThat(tokens.get(8).getType()).isEqualTo(QueryTokenValue.LEXEME);
        assertThat(tokens.get(8).getLexeme()).isEqualTo("s2");
        assertThat(tokens.get(9).getType()).isEqualTo(QueryTokenValue.RIGHT_PAREN);
    }

    @Test
    void shouldParseQueryCorrectly() {
        String query = "Select a such that Follows (\"20\", a)";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(null, query);
        assertThat(tokens).isNotNull().hasSize(12);
        assertThat(tokens.get(0).getType()).isEqualTo(QueryTokenValue.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(QueryTokenValue.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(2).getType()).isEqualTo(QueryTokenValue.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(QueryTokenValue.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(QueryTokenValue.FOLLOWS);
        assertThat(tokens.get(5).getType()).isEqualTo(QueryTokenValue.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(QueryTokenValue.QUOTATION);
        assertThat(tokens.get(7).getType()).isEqualTo(QueryTokenValue.NUMBER);
        assertThat(tokens.get(7).getValue()).isEqualTo(20);
        assertThat(tokens.get(8).getType()).isEqualTo(QueryTokenValue.QUOTATION);
        assertThat(tokens.get(9).getType()).isEqualTo(QueryTokenValue.COMMA);
        assertThat(tokens.get(10).getType()).isEqualTo(QueryTokenValue.LEXEME);
        assertThat(tokens.get(10).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(11).getType()).isEqualTo(QueryTokenValue.RIGHT_PAREN);
    }

    @Test
    void shouldParseComplexQueryCorrectly() {
        String declare = "assign a; while v;\n";
        String query = "Select a such that Modifies (a,v) with v.varName=”x”";
        QueryParser reader = new QueryParser();
        List<QueryToken> tokens = reader.parse(declare, query);
        assertThat(tokens).isNotNull();
        assertThat(tokens).hasSize(24);
    }

}