package ats.v1.query.validator;

import ats.v1.common.Token;
import ats.v1.query.token.QueryToken;

import java.util.List;

public class QueryValidator {

    public void validate(final List<Token<QueryToken>> tokens) {
        //todo jakby się komuś nudziło to można zrefaktorować to trochę, poprzenosić do oddzielnych metod,
        //albo wgl wymyślić jakiś lepszy sposób na to...
        if(tokens.isEmpty()) {
            throw new QueryException("Query is empty [1]");
        }
        if(!tokens.get(0).getType().equals(QueryToken.SELECT)) {
            throw new QueryException("Bad query syntax [2]");
        }
        if(!tokens.get(2).getType().equals(QueryToken.SUCH)) {
            throw new QueryException("Bad query syntax [3]");
        }
        if(!tokens.get(3).getType().equals(QueryToken.THAT)) {
            throw new QueryException("Bad query syntax [4]");
        }

    }
}
