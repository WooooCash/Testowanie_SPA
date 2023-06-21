package ats.v1.query.token;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class QueryTokenValueProvider {

    public QueryTokenValue getTokenByName(final String name) {
        return Arrays.stream(QueryTokenValue.values())
                .filter(token -> token.getValue().equals(name))
                .findFirst()
                .orElse(null);
    }

    public QueryTokenValue getTokenByQueryName(final String queryName) {
        return Arrays.stream(QueryTokenValue.values())
                .filter(token -> token.getQueryValue().equals(queryName))
                .findFirst()
                .orElse(null);
    }

    public List<QueryTokenValue> getAllEssentials() {
        return Arrays.stream(QueryTokenValue.values())
                .filter(queryTokenValue -> queryTokenValue.getType().equals(QueryTokenType.ESSENTIAL))
                .collect(Collectors.toList());
    }

    public List<QueryTokenValue> getAllSuchThat() {
        return Arrays.stream(QueryTokenValue.values())
                .filter(queryTokenValue -> queryTokenValue.getType().equals(QueryTokenType.SUCH_THAT))
                .collect(Collectors.toList());
    }

    public List<QueryTokenValue> getAllDeclarations() {
        return Arrays.stream(QueryTokenValue.values())
                .filter(queryTokenValue -> queryTokenValue.getType().equals(QueryTokenType.DECLARATION))
                .collect(Collectors.toList());
    }

    public boolean isDeclaration(final QueryToken token) {
        return token.type.getType().equals(QueryTokenType.DECLARATION);
    }

    public boolean isEssential(final QueryToken token) {
        return token.type.getType().equals(QueryTokenType.ESSENTIAL);
    }

}
