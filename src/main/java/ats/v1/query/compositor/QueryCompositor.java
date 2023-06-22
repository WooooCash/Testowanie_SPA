package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.token.QueryTokenValue;
import ats.v1.query.token.QueryTokenValueProvider;
import ats.v1.query.validator.QueryException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
public class QueryCompositor {

    private final QueryTokenValueProvider provider = new QueryTokenValueProvider();

    private Query query = new Query();
    private List<Declarations> declarations = new ArrayList<>();

    private boolean isWithoutDeclaration = false;
    private QueryTokenType currentEssential;
    private List<QueryToken> currentTemplateTokens = new ArrayList<>();

    private boolean isEndOfEssential = false;

    public Query composite(final List<QueryToken> tokens) {
        if (tokens.isEmpty()) {
            throw new QueryException("Tokens list is empty.");
        }
        if (tokens.get(0).getType().equals(QueryTokenValue.SELECT)) {
            isWithoutDeclaration = true;
        } else {
            currentEssential = QueryTokenType.DECLARATION;
        }

        for (QueryToken queryToken : tokens) {
            if (!isWithoutDeclaration && currentEssential.equals(QueryTokenType.DECLARATION)) {
                if (!queryToken.getType().equals(QueryTokenValue.SELECT)) {
                    currentTemplateTokens.add(queryToken);
                    continue;
                } else {
                    isEndOfEssential = true;
                    createDeclarations();
                    isWithoutDeclaration = true;
                    currentEssential = queryToken.getType().getType();
                }
            }

        }
        return query;
    }

    private void createDeclarations() {
        QueryTokenValue value = null;
        for (QueryToken token : currentTemplateTokens) {
            if (token.getType().getType().equals(QueryTokenType.DECLARATION)) {
                value = token.getType();
            } else if (token.getType().equals(QueryTokenValue.LEXEME)) {
                declarations.add(new Declarations(value, token.getLexeme()));
            }
        }
    }

    @AllArgsConstructor
    static class Declarations {
        private QueryTokenValue value;
        private String lexeme;
    }

}
