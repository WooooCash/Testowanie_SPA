package ats.v1.query.processor;

import ats.v1.query.compositor.QueryCompositor;
import ats.v1.query.token.QueryToken;
import ats.v1.query.parser.QueryParser;
import ats.v1.query.validator.QueryValidator;

import java.util.List;

public class QueryProcessor {

    private final QueryParser parser = new QueryParser();
    private final QueryValidator validator = new QueryValidator();
    private final QueryCompositor compositor = new QueryCompositor();

    public void process(final String queryString, final String declaration) {
        List<QueryToken> tokens = parser.parse(queryString);
        validator.validate(tokens);
        List<QueryToken> validatedTokens = validator.checkTokens(tokens);
        Query query = compositor.composite(tokens);
        //tworzenie jednego obiektu z listy tokenów
    }
}
