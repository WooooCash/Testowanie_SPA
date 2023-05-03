package ats.v1.query.processor;

import ats.v1.common.Token;
import ats.v1.query.parser.QueryReader;
import ats.v1.query.token.QueryToken;
import ats.v1.query.validator.QueryValidator;

import java.util.List;

public class QueryProcessor {

    private final QueryReader reader = new QueryReader();
    private final QueryValidator validator = new QueryValidator();

    public void process(final String query) {
        List<Token<QueryToken>> tokens = reader.parse(query);
        validator.validate(tokens);
        //tworzenie jednego obiektu z listy tokenów
    }
}
