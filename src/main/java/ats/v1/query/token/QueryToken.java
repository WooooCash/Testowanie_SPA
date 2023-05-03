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
//        values.put(TokenT.THAT, "that");
//        values.put(TokenT.FOLLOWS, "Follows");
//        values.put(TokenT.FOLLOWS_PLUS, "Follows*");
//        values.put(TokenT.PARENT, "Parent");
//        values.put(TokenT.PARENT_PLUS, "Parent*");
//        values.put(TokenT.MODIFIES, "Modifies");
//        values.put(TokenT.WITH, "with");
//        values.put(TokenT.DOT, ".");
//        values.put(TokenT.VARNAME, "varName");
//        values.put(TokenT.STMT, "stmt");
//        values.put(TokenT.EQUALS, "=");
    }




    public TokenT getTokenType(final String value) {
        return values.get(value);
    }
}
