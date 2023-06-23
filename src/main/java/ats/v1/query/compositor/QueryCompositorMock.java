package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QueryCompositorMock {

    public Query composite(final List<QueryToken> tokens) {
        QueryNode select = QueryNode.builder().name("s").nodeType("stmt").build();
        QueryNode suchThat = QueryNode.builder().name("follows").param1(QueryNode.builder().name("s")
                .nodeType("stmt").build()).param2(QueryNode.builder().name("5").nodeType("number").build()).build();
        if (tokens.get(0).getValue() == 1) {
            return new Query(List.of(select), List.of(suchThat), null, null);
        }

        QueryNode select2 = QueryNode.builder().name("w").nodeType("while").build();
        QueryNode suchThat2 = QueryNode.builder().name("uses").param1(QueryNode.builder().name("w")
                .nodeType("while").build()).param2(QueryNode.builder().name("x").nodeType("lexeme").build()).build();
        QueryNode suchThat2_2 = QueryNode.builder().name("modifies").param1(QueryNode.builder().name("w")
                .nodeType("while").build()).param2(QueryNode.builder().name("y").nodeType("lexeme").build()).build();
        if (tokens.get(0).getValue() == 2) {
            return new Query(List.of(select2), List.of(suchThat2, suchThat2_2), null, null);
        }

        QueryNode select3 = QueryNode.builder().name("v").nodeType("variable").build();
        QueryNode suchThat3 = QueryNode.builder().name("modifies").param1(QueryNode.builder().name("7")
                .nodeType("number").build()).param2(QueryNode.builder().name("v").nodeType("variable").build()).build();
        QueryNode suchThat3_2 = QueryNode.builder().name("uses").param1(QueryNode.builder().name("7")
                .nodeType("number").build()).param2(QueryNode.builder().name("v").nodeType("variable").build()).build();
        if (tokens.get(0).getValue() == 3) {
            return new Query(List.of(select3), List.of(suchThat3, suchThat3_2), null, null);
        }
        if (tokens.get(0).getValue() == 4) {
            return new Query(List.of(select3), List.of(suchThat3), null, null);
        }
        return new Query(List.of(select2), List.of(suchThat2), null, null);
    }

    public String mock(final String query) {
        if(query.contains("<"))
            return "none";
        if(query.contains("BOOLEAN") || query.contains("boolean") || query.contains("Boolean"))
            return "true";
        return null;
    }

}
