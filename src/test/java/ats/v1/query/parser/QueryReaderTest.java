package ats.v1.query.parser;

import ats.v1.common.Token;
import ats.v1.query.token.TokenT;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class QueryReaderTest {

    @Test
    void shouldParseSimpleQueryCorrectly() {
        String query = "Select s1 such that Follows (s1, s2)";
        QueryReader reader = new QueryReader();
        List<Token<TokenT>> tokens = reader.parse(query);
        assertThat(tokens).isNotNull().hasSize(10);
        assertThat(tokens.get(0).getType()).isEqualTo(TokenT.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(TokenT.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(2).getType()).isEqualTo(TokenT.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(TokenT.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(TokenT.FOLLOWS);
        assertThat(tokens.get(5).getType()).isEqualTo(TokenT.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(TokenT.LEXEME);
        assertThat(tokens.get(6).getLexeme()).isEqualTo("s1");
        assertThat(tokens.get(7).getType()).isEqualTo(TokenT.AND);
        assertThat(tokens.get(8).getType()).isEqualTo(TokenT.LEXEME);
        assertThat(tokens.get(8).getLexeme()).isEqualTo("s2");
        assertThat(tokens.get(9).getType()).isEqualTo(TokenT.RIGHT_PAREN);
    }

    @Test
    void shouldParseQueryCorrectly() {
        String query = "Select a such that Follows* (20, a)";
        QueryReader reader = new QueryReader();
        List<Token<TokenT>> tokens = reader.parse(query);
        assertThat(tokens).isNotNull().hasSize(10);
        assertThat(tokens.get(0).getType()).isEqualTo(TokenT.SELECT);
        assertThat(tokens.get(1).getType()).isEqualTo(TokenT.LEXEME);
        assertThat(tokens.get(1).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(2).getType()).isEqualTo(TokenT.SUCH);
        assertThat(tokens.get(3).getType()).isEqualTo(TokenT.THAT);
        assertThat(tokens.get(4).getType()).isEqualTo(TokenT.FOLLOWS_PLUS);
        assertThat(tokens.get(5).getType()).isEqualTo(TokenT.LEFT_PAREN);
        assertThat(tokens.get(6).getType()).isEqualTo(TokenT.NUMBER);
        assertThat(tokens.get(6).getValue()).isEqualTo(20);
        assertThat(tokens.get(7).getType()).isEqualTo(TokenT.AND);
        assertThat(tokens.get(8).getType()).isEqualTo(TokenT.LEXEME);
        assertThat(tokens.get(8).getLexeme()).isEqualTo("a");
        assertThat(tokens.get(9).getType()).isEqualTo(TokenT.RIGHT_PAREN);
    }

    @Test
    void shouldParseComplexQueryCorrectly() {
        String query = "Select a such that Modifies (a,v) with v.varName=”x”";
        QueryReader reader = new QueryReader();
        List<Token<TokenT>> tokens = reader.parse(query);
        assertThat(tokens).isNotNull();
        assertThat(tokens).hasSize(18);
    }

}