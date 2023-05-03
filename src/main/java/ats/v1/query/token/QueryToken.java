package ats.v1.query.token;

import java.util.HashMap;
import java.util.Map;

public class QueryToken {

    private final Map<String, TokenT> values = new HashMap<>();

    public QueryToken() {
        initValues();
    }

    private void initValues() {
        values.put("Select", TokenT.SELECT);
        values.put("such", TokenT.SUCH);
        values.put("that", TokenT.THAT);
        values.put("Follows", TokenT.FOLLOWS);
        values.put("Follows*", TokenT.FOLLOWS_PLUS);
        values.put("Parent", TokenT.PARENT);
        values.put("Parent*", TokenT.PARENT_PLUS);
        values.put("Modifies", TokenT.MODIFIES);
        values.put("with", TokenT.WITH);
        values.put(".", TokenT.DOT);
        values.put(",", TokenT.AND);
        values.put("”", TokenT.QUOTATION);
        values.put("varName", TokenT.VARNAME);
        values.put("stmt", TokenT.STMT);
        values.put("=", TokenT.EQUALS);
        values.put("(", TokenT.LEFT_PAREN);
        values.put(")", TokenT.RIGHT_PAREN);
    }


    public TokenT getTokenType(final String value) {
        return values.get(value);
    }
}
