package ats.v1.query.token;

import ats.v1.query.QueryTestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class QueryTokenTableWriterTest {

    @Test
    void shouldWriteTokenTableCorrectly() {
        List<QueryToken> tokens = QueryTestUtils.getTokens();
        QueryTokenTableWriter tableWriter = new QueryTokenTableWriter();
        String table = tableWriter.writeQueryTokenTable(tokens);
        assertThat(table).isNotEmpty();
        assertThat(table).isEqualTo(getExpectedValue());
    }

    private static String getExpectedValue() {
        return "QUERY TOKEN TABLE\n" +
                "_____________________________________________\n" +
                "| INDEX |         TYPE |     LEXEME | VALUE |\n" +
                "|     0 |       SELECT |       null |     0 |\n" +
                "|     1 |       LEXEME |          a |     0 |\n" +
                "|     2 |         SUCH |       null |     0 |\n" +
                "|     3 |         THAT |       null |     0 |\n" +
                "|     4 |     MODIFIES |       null |     0 |\n" +
                "|     5 |   LEFT_PAREN |       null |     0 |\n" +
                "|     6 |       LEXEME |          a |     0 |\n" +
                "|     7 |        COMMA |       null |     0 |\n" +
                "|     8 |       LEXEME |          v |     0 |\n" +
                "|     9 |  RIGHT_PAREN |       null |     0 |\n" +
                "|    10 |         WITH |       null |     0 |\n" +
                "|    11 |       LEXEME |          v |     0 |\n" +
                "|    12 |          DOT |       null |     0 |\n" +
                "|    13 |      VARNAME |       null |     0 |\n" +
                "|    14 |       EQUALS |       null |     0 |\n" +
                "|    15 |    QUOTATION |       null |     0 |\n" +
                "|    16 |       LEXEME |          x |     0 |\n" +
                "|    17 |    QUOTATION |       null |     0 |\n" +
                "_____________________________________________\n";
    }

}