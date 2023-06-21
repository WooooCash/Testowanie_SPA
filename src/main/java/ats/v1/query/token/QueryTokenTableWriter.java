package ats.v1.query.token;

import java.util.List;

public class QueryTokenTableWriter {
    public String writeQueryTokenTable(List<QueryToken> tokens) {
        StringBuilder tableStr = new StringBuilder("QUERY TOKEN TABLE\n");
        tableStr.append("_____________________________________________\n");
        tableStr.append(String.format("| %5s | %12s | %10s | %5s |\n", "INDEX", "TYPE", "LEXEME", "VALUE"));
        for (int i = 0; i < tokens.size(); i++) {
            tableStr.append(String.format("| %5s | %12s | %10s | %5s |\n", i, tokens.get(i).getType(),
                    tokens.get(i).getLexeme(), tokens.get(i).getValue()));
        }
        tableStr.append("_____________________________________________\n");
        return tableStr.toString();
    }
}
