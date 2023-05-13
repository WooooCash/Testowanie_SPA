package ats.v1.query.validator;

import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;

import java.util.List;

public class QueryValidator {

    public void validate(final List<QueryToken> tokens) {
        //todo jakby się komuś nudziło to można zrefaktorować to trochę, poprzenosić do oddzielnych metod,
        //albo wgl wymyślić jakiś lepszy sposób na to...
        if(tokens.isEmpty()) {
            throw new QueryException("Query is empty [1]");
        }
        if(!tokens.get(0).getType().equals(QueryTokenType.SELECT)) {
            throw new QueryException("Bad query syntax [2]");
        }
        if(!tokens.get(2).getType().equals(QueryTokenType.SUCH)) {
            throw new QueryException("Bad query syntax [3]");
        }
        if(!tokens.get(3).getType().equals(QueryTokenType.THAT)) {
            throw new QueryException("Bad query syntax [4]");
        }

    }
}
