package ats.v1.query.processor;

import ats.v1.query.token.QueryToken;
import ats.v1.query.parser.QueryParser;
import ats.v1.query.validator.QueryValidator;

import java.util.List;

public class QueryProcessor {

    private final QueryParser parser = new QueryParser();
    private final QueryValidator validator = new QueryValidator();

    public void process(final String query) {
        List<QueryToken> tokens = parser.parse(query);
        validator.validate(tokens);
        //tworzenie jednego obiektu z listy tokenów
    }
}
