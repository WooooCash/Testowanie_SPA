package ats.v1.query.validator;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.token.QueryTokenTypeProvider;

import java.util.List;
import java.util.stream.Collectors;

public class QueryValidator {

    private final QueryTokenTypeProvider provider = new QueryTokenTypeProvider();

    public List<QueryToken> checkTokens(final List<QueryToken> tokens) {
        return tokens.stream().map(this::check).collect(Collectors.toList());
    }

    private QueryToken check(final QueryToken token) {
        QueryTokenType tokenType = provider.getTokenByQueryName(token.getLexeme());
        return tokenType == null ? token : QueryToken.builder().type(tokenType).build();
    }

}
