package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenGroup;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.token.QueryTokenTypeProvider;
import ats.v1.query.validator.QueryException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor
public class QueryCompositor {

    private final QueryTokenTypeProvider provider = new QueryTokenTypeProvider();

    private Query query = new Query();
    private List<Declaration> declarations = new ArrayList<>();
    private boolean isWithoutDeclaration = false;
    private QueryTokenType currentEssential;
    private List<QueryToken> currentTemplateTokens = new ArrayList<>();



//    private boolean isEndOfEssential = false;

    public Query composite(final List<QueryToken> tokens) {
        if (tokens.isEmpty()) {
            throw new QueryException("Tokens list is empty.");
        }

        if (tokens.get(0).getType().equals(QueryTokenType.SELECT)) {
            isWithoutDeclaration = true;
        } else {
            currentEssential = tokens.get(0).getType();
        }

        for (QueryToken queryToken : tokens) {
            if (!isWithoutDeclaration && currentEssential.getGroup().equals(QueryTokenGroup.DECLARATION)) {
                if (!queryToken.getType().equals(QueryTokenType.SELECT)) {
                    currentTemplateTokens.add(queryToken);
                    continue;
                } else {
                    createDeclarations();
                    isWithoutDeclaration = true;
                    currentEssential = queryToken.getType();
                    currentTemplateTokens.clear();
                }
            }
            if (currentEssential.equals(QueryTokenType.SELECT)) {
                if (!queryToken.getType().getGroup().equals(QueryTokenGroup.ESSENTIAL)) {
                    currentTemplateTokens.add(queryToken);
                    continue;
                } else {
                    createResult();
                    currentEssential = queryToken.getType();
                    currentTemplateTokens.clear();
                }
            }

        }
        return query;
    }

    private void createDeclarations() {
        QueryTokenType value = null;
        for (QueryToken token : currentTemplateTokens) {
            if (token.getType().getGroup().equals(QueryTokenGroup.DECLARATION)) {
                value = token.getType();
            } else if (token.getType().equals(QueryTokenType.LEXEME)) {
                declarations.add(new Declaration(value, token.getLexeme()));
            }
        }
    }

    private void createResult() {
        List<QueryNode> resultNodes = new ArrayList<>();
        for (QueryToken token : currentTemplateTokens) {
            if(!token.getType().equals(QueryTokenType.COMMA)){
                QueryTokenType inDeclarations = isInDeclarations(token);
                if(inDeclarations != null) {
                    resultNodes.add(QueryNode.builder().name(token.getLexeme()).nodeType(inDeclarations.getQueryValue()).build());
                }
                else {
                    resultNodes.add(QueryNode.builder().name(token.getLexeme()).build());
                }
            }
        }
        query.getResult().addAll(resultNodes);
    }

    @AllArgsConstructor
    static class Declaration {
        private QueryTokenType value;
        private String lexeme;
    }

    private QueryTokenType isInDeclarations(final QueryToken token) {
        for(Declaration declaration : declarations) {
            if(declaration.lexeme.equals(token.getLexeme())){
                return declaration.value;
            }
        }
        return null;
    }

}
