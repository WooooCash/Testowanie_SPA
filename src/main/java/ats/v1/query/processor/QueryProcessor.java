package ats.v1.query.processor;

import ats.v1.pkb.Pkb;
import ats.v1.query.compositor.QueryCompositor;
import ats.v1.query.evaluator.QueryEvaluator;
import ats.v1.query.parser.QueryParser;
import ats.v1.query.token.QueryToken;
import ats.v1.query.validator.QueryValidator;

import java.util.List;

public class QueryProcessor {
    private final QueryParser parser = new QueryParser();
    private final QueryValidator validator = new QueryValidator();
    private final QueryCompositor compositor = new QueryCompositor();
    private final QueryEvaluator evaluator = new QueryEvaluator();

    public String process(final String queryString, final String declaration, final Pkb pkb) {
        List<QueryToken> tokens = parser.parse(declaration, queryString);
      //  validator.validate(tokens);

        List<QueryToken> validatedTokens = validator.checkTokens(tokens);
        Query query = compositor.composite(validatedTokens);
//        Query query = queryCompositorMock.composite(List.of(QueryToken.builder().value(1).build())); //todo do wywalenia po beta testach

        return evaluator.evaluate(pkb, query);
    }

}
