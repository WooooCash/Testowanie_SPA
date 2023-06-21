package ats.v1.query.compositor;

import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.token.QueryToken;
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

    private final List<QueryToken> tokens;
    private Query query = new Query();
    private QueryTokenValue currentProcessedToken = null;
    private List<QueryToken> currentTokens = new ArrayList<>();
    private boolean isEndOfEssentialType = false;

    public Query composite(final List<QueryToken> tokens) {
        if (tokens.isEmpty()) {
            throw new QueryException("Tokens list is empty.");
        }
        for (QueryToken queryToken : tokens) {
            if (provider.getAllDeclarations().contains(queryToken.getType())) {

            }
//            if (provider.getAllEssentials().contains(queryToken.getType())) {
//                currentProcessedToken = queryToken.getType();
//                isEndOfEssentialType = false;
//            }
//            if (currentProcessedToken != null && !isEndOfEssentialType && !provider.getAllEssentials().contains(queryToken.getType())) {
//                currentTokens.add(queryToken);
//            }
//            if (currentProcessedToken != null && !isEndOfEssentialType && provider.getAllEssentials().contains(queryToken.getType())) {
//                isEndOfEssentialType = true;
//                makePart();
//                clean();
//                currentProcessedToken = queryToken.getType();
//            }
        }
        return query;
    }

    private void makePart() {
        switch (currentProcessedToken) {
            case SELECT:
                makeSelect();
                break;
            case THAT:
                makeSuchThat();
            case WITH:
                makeWith();
                break;
        }
    }

    private void makeSelect() {
//        if (currentTokens.size() == 1) { //TODO na razie tylko jeden może być, później dodać resztę warunków
//            QueryTokenValue type = currentTokens.get(0).getType();
//            if (type.equals(QueryTokenValue.NUMBER)) {
//                query.setResult(QueryNode.builder().name(String.valueOf(currentTokens.get(0).getValue())).build());
//            }
//            if (type.equals(QueryTokenValue.LEXEME)) {
//                query.setResult(QueryNode.builder().name(String.valueOf(currentTokens.get(0).getLexeme())).build());
//            }
//        }
    }

    private List<QueryToken> getAllTokenTillNextHead() {

    }

    private void makeSuchThat() {
//        QueryToken firstToken = currentTokens.get(0);
//        List<QueryNode> args = new ArrayList<>();
//        if(!provider.getAllSuchThat().contains(firstToken.getType())) {
//            throw new QueryException("Bad type in such that");
//        }
//        for(int i = 1; i < currentTokens.size(); i++) {
//            if (currentTokens.get(i).getType().equals(QueryTokenValue.LEFT_PAREN)) {
//                i++;
//                while (!currentTokens.get(i).getType().equals(QueryTokenValue.RIGHT_PAREN)) {
//                    if (currentTokens.get(i).getType().equals(QueryTokenValue.COMMA)) {
//                        i++;
//                        continue;
//                    }
//                    if (currentTokens.get(i).getType().equals(QueryTokenValue.NUMBER)) {
//                        args.add(QueryNode.builder().name(String.valueOf(currentTokens.get(i).getValue())).build());
//                    }
//                    if (currentTokens.get(i).getType().equals(QueryTokenValue.LEXEME)) {
//                        args.add(QueryNode.builder().name(currentTokens.get(i).getLexeme()).build());
//                    }
//                    i++;
//                }
//            }
//        }
//        query.setSuchThat(QueryNode.builder().name(firstToken.getType().name()).children(args).build());
    }

    private void makeWith() {
//        QueryToken firstToken = currentTokens.get(0);
//        List<QueryNode> args = new ArrayList<>();
//        if (provider.getAllSuchThat().contains(firstToken.getType())) {
//            throw new QueryException("Bad type in such that");
//        }
        //in progress
    }

    private void clean() {
        currentTokens.clear();
        isEndOfEssentialType = false;
    }

}
