package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.processor.QueryWithNode;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenGroup;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.token.QueryTokenTypeProvider;
import ats.v1.query.validator.QueryException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(force = true)
@AllArgsConstructor
public class QueryCompositor {

    private Set<QueryTokenType> quotations = Set.of(QueryTokenType.QUOTATION, QueryTokenType.QUOTATION2, QueryTokenType.QUOTATION3);
    private final QueryTokenTypeProvider provider = new QueryTokenTypeProvider();

    private Query query;
    private List<Declaration> declarations;
    private boolean isWithoutDeclaration = false;
    private QueryTokenType currentEssential;
    private List<QueryToken> currentTemplateTokens;

    public Query composite(final List<QueryToken> tokens) {
        query = new Query();
        declarations = new ArrayList<>();
        currentTemplateTokens = new ArrayList<>();
        if (tokens.isEmpty()) {
            throw new QueryException("Tokens list is empty.");
        }
        isWithoutDeclaration = tokens.get(0).getType().equals(QueryTokenType.SELECT);
        currentEssential = tokens.get(0).getType();
        for (QueryToken queryToken : tokens) {
            if (!isWithoutDeclaration && currentEssential.getGroup().equals(QueryTokenGroup.DECLARATION)) {
                if (!queryToken.getType().equals(QueryTokenType.SELECT)) {
                    currentTemplateTokens.add(queryToken);
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
                } else {
                    createResult();
                    currentEssential = queryToken.getType();
                    currentTemplateTokens.clear();
                }
            }
            if (currentEssential.equals(QueryTokenType.SUCH)) {
                currentEssential = queryToken.getType();
            }
            if (currentEssential.equals(QueryTokenType.THAT)) {
                if (!queryToken.getType().getGroup().equals(QueryTokenGroup.ESSENTIAL)) {
                    currentTemplateTokens.add(queryToken);
                } else {
                    createSuchThat();
                    currentEssential = queryToken.getType();
                    currentTemplateTokens.clear();
                }
            }
            if (currentEssential.equals(QueryTokenType.WITH)) {
                if (!queryToken.getType().getGroup().equals(QueryTokenGroup.ESSENTIAL)) {
                    currentTemplateTokens.add(queryToken);
                } else {
                    createWith();
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
            if (!token.getType().equals(QueryTokenType.COMMA)) {
                QueryTokenType inDeclarations = isInDeclarations(token);
                if (inDeclarations != null) {
                    resultNodes.add(QueryNode.builder().name(token.getLexeme()).nodeType(inDeclarations.getQueryValue()).build());
                } else {
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
        for (int i = 0; i < currentTemplateTokens.size(); i++) {
            if (currentTemplateTokens.get(i).getType().getGroup().equals(QueryTokenGroup.SUCH_THAT)) {
                currentType = currentTemplateTokens.get(i).getType();
            }
            if (currentTemplateTokens.get(i).getType().equals(QueryTokenType.LEFT_PAREN)) {
                while (!currentTemplateTokens.get(i).getType().equals(QueryTokenType.RIGHT_PAREN)) {
                    //if (currentTemplateTokens.get(i).getType().equals(QueryTokenType.QUOTATION)) {
                    if (quotations.contains(currentTemplateTokens.get(i).getType())) {
                        i++;
                        while (!quotations.contains(currentTemplateTokens.get(i).getType())) {
                            currentParams.add(QueryNode.builder().name(currentTemplateTokens.get(i).getLexeme()).nodeType("lexeme").build());
                            i++;
                        }
                    }
                    QueryTokenType inDeclarations = isInDeclarations(currentTemplateTokens.get(i));
                    if (inDeclarations != null) {
                        currentParams.add(QueryNode.builder().name(currentTemplateTokens.get(i).getLexeme()).nodeType(inDeclarations.getQueryValue()).build());
                    }
                    if (currentTemplateTokens.get(i).getValue() != null) {
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

    private void createWith() {
        QueryWithNode queryWithNode = new QueryWithNode();
        for (int i = 0; i < currentTemplateTokens.size(); i++) {
            if (currentTemplateTokens.get(i).getType().equals(QueryTokenType.AND)) {
                query.getWith().add(queryWithNode);
                queryWithNode = new QueryWithNode();
            }
            QueryTokenType inDeclarations = isInDeclarations(currentTemplateTokens.get(i));
            if (inDeclarations != null) {
                queryWithNode.setFirstParamName(currentTemplateTokens.get(i).getLexeme());
                queryWithNode.setFirstParamType(inDeclarations.getQueryValue());
                queryWithNode.setFirstParamArgument(currentTemplateTokens.get(i + 2).getType().getQueryValue());
                if (currentTemplateTokens.get(i + 3).getType().equals(QueryTokenType.HASH)) {
                    queryWithNode.setFirstParamHash(true);
                }
            }
            if (currentTemplateTokens.get(i).getType().equals(QueryTokenType.EQUALS)) {
                if (currentTemplateTokens.get(i + 1).getType().equals(QueryTokenType.NUMBER)) {
                    queryWithNode.setSecondParamType("number");
                    queryWithNode.setSecondParamValue(currentTemplateTokens.get(i + 1).getValue());
                }
                if (quotations.contains(currentTemplateTokens.get(i + 1).getType())) {
                    queryWithNode.setSecondParamName(currentTemplateTokens.get(i + 2).getLexeme());
                    queryWithNode.setSecondParamType("lexeme");
                }
                QueryTokenType inDeclarations1 = isInDeclarations(currentTemplateTokens.get(i + 1));
                if (inDeclarations1 != null) {
                    queryWithNode.setSecondParamName(currentTemplateTokens.get(i + 1).getLexeme());
                    queryWithNode.setSecondParamType(inDeclarations1.getQueryValue());
                    queryWithNode.setSecondParamArgument(currentTemplateTokens.get(i + 3).getLexeme());
                    if (currentTemplateTokens.get(i + 4).getType().equals(QueryTokenType.HASH)) {
                        queryWithNode.setSecondParamHash(true);
                    }
                }
            }
        }
        if (!queryWithNode.getFirstParamName().isEmpty()) {
            query.getWith().add(queryWithNode);
        }
    }

    @AllArgsConstructor
    static class Declaration {
        private QueryTokenType value;
        private String lexeme;
    }

    private QueryTokenType isInDeclarations(final QueryToken token) {
        for (Declaration declaration : declarations) {
            if (declaration.lexeme.equals(token.getLexeme())) {
                return declaration.value;
            }
        }
        return null;
    }

}
