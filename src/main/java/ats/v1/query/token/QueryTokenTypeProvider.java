package ats.v1.query.token;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class QueryTokenTypeProvider {

    public QueryTokenType getTokenByName(final String name) {
        return Arrays.stream(QueryTokenType.values())
                .filter(token -> token.getValue().equals(name))
                .findFirst()
                .orElse(null);
    }

    public QueryTokenType getTokenByQueryName(final String queryName) {
        return Arrays.stream(QueryTokenType.values())
                .filter(token -> token.getQueryValue().equals(queryName))
                .findFirst()
                .orElse(null);
    }

    public List<QueryTokenType> getAllEssentials() {
        return Arrays.stream(QueryTokenType.values())
                .filter(queryTokenValue -> queryTokenValue.getGroup().equals(QueryTokenGroup.ESSENTIAL))
                .collect(Collectors.toList());
    }

    public List<QueryTokenType> getAllSuchThat() {
        return Arrays.stream(QueryTokenType.values())
                .filter(queryTokenValue -> queryTokenValue.getGroup().equals(QueryTokenGroup.SUCH_THAT))
                .collect(Collectors.toList());
    }

    public List<QueryTokenType> getAllDeclarations() {
        return Arrays.stream(QueryTokenType.values())
                .filter(queryTokenValue -> queryTokenValue.getGroup().equals(QueryTokenGroup.DECLARATION))
                .collect(Collectors.toList());
    }

    public boolean isDeclaration(final QueryToken token) {
        return token.type.getGroup().equals(QueryTokenGroup.DECLARATION);
    }

    public boolean isEssential(final QueryToken token) {
        return token.type.getGroup().equals(QueryTokenGroup.ESSENTIAL);
    }

}
