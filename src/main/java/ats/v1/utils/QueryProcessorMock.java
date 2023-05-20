package ats.v1.utils;

public class QueryProcessorMock {

    public String process(final String query, final String declare) {
        String[] split = declare.split(" ");
        String assign = "";
        if (split.length == 2) {
            assign = split[1];
        }
        if (query.contains("Modifies") && !query.contains("with") && !assign.equals("")) {
            return assign;
        }
        if (query.contains("Modifies") && query.contains(",")) {
            return "FALSE";
        }
        if (query.contains("Follows*")) {
            return "1, 2, 3, 5, 6, 7";
        }
        if (query.contains("Follows")) {
            return "1, 3, 5, 6, 7";
        }
        if (query.contains("Uses") && !query.contains(",") && !assign.equals("")){
            return "1, 2, 4, 6";
        }

        if (query.contains("Uses") && !query.contains(",")){
            return "1, 2, 3, 4, 5, 6";
        }
            return "cos poszlo nie tak";
    }
}
