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
            if(currentEssential.equals(QueryTokenType.SUCH)) {
                currentEssential = queryToken.getType();
                continue;
            }
            if(currentEssential.equals(QueryTokenType.THAT)) {
                if (!queryToken.getType().getGroup().equals(QueryTokenGroup.ESSENTIAL)) {
                    currentTemplateTokens.add(queryToken);
                } else {
                    createSuchThat();
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

    private void createSuchThat() {
        QueryTokenType currentType = null;
        List<QueryNode> currentParams = new ArrayList<>();
        List<QueryNode> suchThatNodes = new ArrayList<>();
        for(int i = 0; i < currentTemplateTokens.size(); i++){
            if(currentTemplateTokens.get(i).getType().getGroup().equals(QueryTokenGroup.SUCH_THAT)) {
                currentType = currentTemplateTokens.get(i).getType();
            }
            if(currentTemplateTokens.get(i).getType().equals(QueryTokenType.LEFT_PAREN)) {
                while (!currentTemplateTokens.get(i).getType().equals(QueryTokenType.RIGHT_PAREN)) {
                    if(currentTemplateTokens.get(i).getType().equals(QueryTokenType.QUOTATION)) {
                        i++;
                        while (!currentTemplateTokens.get(i).getType().equals(QueryTokenType.QUOTATION)) {
                            currentParams.add(QueryNode.builder().name(currentTemplateTokens.get(i).getLexeme()).nodeType("lexeme").build());
                            i++;
                        }
                    }
                    QueryTokenType inDeclarations = isInDeclarations(currentTemplateTokens.get(i));
                    if(inDeclarations != null) {
                        currentParams.add(QueryNode.builder().name(currentTemplateTokens.get(i).getLexeme()).nodeType(inDeclarations.getQueryValue()).build());
                    }
                    if(currentTemplateTokens.get(i).getValue() != null) {
                        currentParams.add(QueryNode.builder().name(String.valueOf(currentTemplateTokens.get(i).getValue())).nodeType("number").build());
                    }
                    i++;
                }
                suchThatNodes.add(QueryNode.builder().name(currentType.getQueryValue()).param1(currentParams.get(0)).param2(currentParams.get(1)).build());
                currentParams.clear();
                i++;
            }
        }
        query.getSuchThat().addAll(suchThatNodes);
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
