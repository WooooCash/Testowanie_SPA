package ats.v1.query.token;

import java.util.HashMap;
import java.util.Map;

public class QueryTokenValue {

    private final Map<String, QueryToken> values = new HashMap<>();

    public QueryTokenValue() {
        initValues();
    }

    private void initValues() {
        values.put("Select", QueryToken.SELECT);
        values.put("such", QueryToken.SUCH);
        values.put("that", QueryToken.THAT);
        values.put("Follows", QueryToken.FOLLOWS);
        values.put("Follows*", QueryToken.FOLLOWS_PLUS);
        values.put("Parent", QueryToken.PARENT);
        values.put("Parent*", QueryToken.PARENT_PLUS);
        values.put("Modifies", QueryToken.MODIFIES);
        values.put("with", QueryToken.WITH);
        values.put(".", QueryToken.DOT);
        values.put(",", QueryToken.COMMA);
        values.put("”", QueryToken.QUOTATION);
        values.put("varName", QueryToken.VARNAME);
        values.put("stmt", QueryToken.STMT);
        values.put("=", QueryToken.EQUALS);
        values.put("(", QueryToken.LEFT_PAREN);
        values.put(")", QueryToken.RIGHT_PAREN);
        values.put("and", QueryToken.AND);
    }


    public QueryToken getTokenType(final String value) {
        return values.get(value);
    }
}
