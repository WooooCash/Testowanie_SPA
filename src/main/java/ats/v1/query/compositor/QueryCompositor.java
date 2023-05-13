package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.token.QueryToken;
import ats.v1.query.token.QueryTokenType;
import ats.v1.query.validator.QueryException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QueryCompositor {

    private final List<QueryToken> tokens;

    private Query query = new Query();

    private QueryTokenType currentProcessedToken = null;

    private List<QueryToken> currentTokens = new ArrayList<>();

    private boolean isEndOfEssentialType = false;

    public Query composite(final List<QueryToken> tokens) {
        if (tokens.isEmpty()) {
            throw new QueryException("Tokens list is empty.");
        }
        for (QueryToken queryToken : tokens) {
            if(essentialTypes().contains(queryToken.getType()) && currentProcessedToken == null) {
                currentProcessedToken = queryToken.getType();
                isEndOfEssentialType = false;
            }
            if(currentProcessedToken != null && !isEndOfEssentialType && !essentialTypes().contains(queryToken.getType())) {
                currentTokens.add(queryToken);
            }
            if(currentProcessedToken != null && !isEndOfEssentialType && essentialTypes().contains(queryToken.getType())) {
                isEndOfEssentialType = true;
                makePart();
                clean();
                currentProcessedToken = queryToken.getType();
            }
        }
        return query;
    }


    private List<QueryTokenType> essentialTypes() {
        return List.of(QueryTokenType.SELECT,
                QueryTokenType.SUCH,
                QueryTokenType.THAT,
                QueryTokenType.WITH,
                QueryTokenType.PARENT);
    }

    private List<QueryTokenType> modifiesTypes() { //TODO nazwa...
        return List.of(QueryTokenType.MODIFIES,
                QueryTokenType.FOLLOWS,
                QueryTokenType.FOLLOWS_PLUS //todo reszta
                );
    }

    private void makePart() {
        switch (currentProcessedToken) {
            case SELECT:
                makeSelect();
                break;
            case SUCH:
                break;
            case THAT:
                makeThat();

        }
    }

    private void makeSelect() {
        if(currentTokens.size() == 1) { //TODO na razie tylko jeden może być, później dodać resztę warunków
            QueryTokenType type = currentTokens.get(0).getType();
            if(type.equals(QueryTokenType.NUMBER)) {
                query.setResult(QueryNode.builder().name(String.valueOf(currentTokens.get(0).getValue())).build());
            }
            if(type.equals(QueryTokenType.LEXEME)) {
                query.setResult(QueryNode.builder().name(String.valueOf(currentTokens.get(0).getLexeme())).build());
            }
        }
    }

    private void makeThat() {
        QueryToken firstToken = currentTokens.get(0);
        List<QueryNode> args = new ArrayList<>();
        if(modifiesTypes().contains(firstToken.getType())) {
            throw new QueryException("Bad type in such that");
        }
        for(int i = 1; i < currentTokens.size(); i++) {
            if(currentTokens.get(i).getType().equals(QueryTokenType.LEFT_PAREN)) {
                i++;
                while(!currentTokens.get(i).getType().equals(QueryTokenType.RIGHT_PAREN)) {
                    if(currentTokens.get(i).getType().equals(QueryTokenType.COMMA)) {
                        continue;
                    }
                    if(currentTokens.get(i).getType().equals(QueryTokenType.NUMBER)) {
                        args.add(QueryNode.builder().name(String.valueOf(currentTokens.get(i).getValue())).build());
                    }
                    if(currentTokens.get(i).getType().equals(QueryTokenType.LEXEME)) {
                        args.add(QueryNode.builder().name(currentTokens.get(i).getLexeme()).build());
                    }
                }
            }
        }
        query.setSuchThat(QueryNode.builder().name(firstToken.getType().name()).children(args).build());
    }

    private void clean() {
        currentTokens.clear();
        isEndOfEssentialType = false;
    }
}
