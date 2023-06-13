package ats.v1.query.token;

import java.util.HashMap;
import java.util.Map;

public class QueryTokenValue2 { //TODO DO WYJEBANIA PO PRZENIESIENIU WSZYSTKICH TOKENÓW DO QueryTokenValue

    private final Map<String, QueryTokenValue> values = new HashMap<>();

    public QueryTokenValue2() {
        initValues();
    }

    private void initValues() {
        values.put("select", QueryTokenValue.SELECT);
        values.put("such", QueryTokenValue.SUCH);
        values.put("that", QueryTokenValue.THAT);
        values.put("follows", QueryTokenValue.FOLLOWS);
        values.put("follows*", QueryTokenValue.FOLLOWS_PLUS);
        values.put("parent", QueryTokenValue.PARENT);
        values.put("parent*", QueryTokenValue.PARENT_PLUS);
        values.put("modifies", QueryTokenValue.MODIFIES);
        values.put("with", QueryTokenValue.WITH);
        values.put(".", QueryTokenValue.DOT);
        values.put(",", QueryTokenValue.COMMA);
        values.put("”", QueryTokenValue.QUOTATION);
        values.put("\"", QueryTokenValue.QUOTATION);
        values.put("'", QueryTokenValue.QUOTATION);
        values.put("varname", QueryTokenValue.VARNAME);
        values.put("stmt", QueryTokenValue.STMT);
        values.put("=", QueryTokenValue.EQUALS);
        values.put("(", QueryTokenValue.LEFT_PAREN);
        values.put(")", QueryTokenValue.RIGHT_PAREN);
        values.put("and", QueryTokenValue.AND);
        values.put("\n", QueryTokenValue.EOL);
        values.put("\r\n", QueryTokenValue.EOL);
        values.put(";", QueryTokenValue.SEMICOLON);
    }

    public QueryTokenValue getTokenType(final String value) {
        return values.get(value);
    }
}
