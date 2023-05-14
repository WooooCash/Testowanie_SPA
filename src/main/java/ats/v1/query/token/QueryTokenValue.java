package ats.v1.query.token;

import java.util.HashMap;
import java.util.Map;

public class QueryTokenValue {

    private final Map<String, QueryTokenType> values = new HashMap<>();

    public QueryTokenValue() {
        initValues();
    }

    private void initValues() {
        values.put("select", QueryTokenType.SELECT);
        values.put("such", QueryTokenType.SUCH);
        values.put("that", QueryTokenType.THAT);
        values.put("follows", QueryTokenType.FOLLOWS);
        values.put("follows*", QueryTokenType.FOLLOWS_PLUS);
        values.put("parent", QueryTokenType.PARENT);
        values.put("parent*", QueryTokenType.PARENT_PLUS);
        values.put("modifies", QueryTokenType.MODIFIES);
        values.put("with", QueryTokenType.WITH);
        values.put(".", QueryTokenType.DOT);
        values.put(",", QueryTokenType.COMMA);
        values.put("”", QueryTokenType.QUOTATION);
        values.put("varname", QueryTokenType.VARNAME);
        values.put("stmt", QueryTokenType.STMT);
        values.put("=", QueryTokenType.EQUALS);
        values.put("(", QueryTokenType.LEFT_PAREN);
        values.put(")", QueryTokenType.RIGHT_PAREN);
        values.put("and", QueryTokenType.AND);
        values.put("\n", QueryTokenType.EOL);
        values.put("\r\n", QueryTokenType.EOL);
        values.put(";", QueryTokenType.SEMICOLON);
    }

    public QueryTokenType getTokenType(final String value) {
        return values.get(value);
    }
}
