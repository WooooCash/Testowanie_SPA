package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.token.QueryToken;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QueryCompositorMock {

    public Query composite(final List<QueryToken> tokens) {
        QueryNode root = QueryNode.builder().name("s").nodeType("stmt").build();
        QueryNode select = QueryNode.builder().name("s").nodeType("stmt").build();
        QueryNode suchThat = QueryNode.builder().name("follows").param1(QueryNode.builder().name("s")
                .nodeType("stmt").build()).param2(QueryNode.builder().name("5").nodeType("number").build()).build();
        if (tokens.get(0).getValue() == 1) {
            return new Query(List.of(root), List.of(select), List.of(suchThat), null, null);
        }

        QueryNode root2 = QueryNode.builder().name("w").nodeType("while").build();
        QueryNode select2 = QueryNode.builder().name("w").nodeType("while").build();
        QueryNode suchThat2 = QueryNode.builder().name("uses").param1(QueryNode.builder().name("w")
                .nodeType("while").build()).param2(QueryNode.builder().name("x").nodeType("lexeme").build()).build();
        QueryNode suchThat2_2 = QueryNode.builder().name("modifies").param1(QueryNode.builder().name("w")
                .nodeType("while").build()).param2(QueryNode.builder().name("y").nodeType("lexeme").build()).build();
        if (tokens.get(0).getValue() == 2) {
            return new Query(List.of(root2), List.of(select2), List.of(suchThat2, suchThat2_2), null, null);
        }
        return new Query(List.of(root2), List.of(select2), List.of(suchThat2), null, null);
    }
}
