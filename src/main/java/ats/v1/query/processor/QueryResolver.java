package ats.v1.query.processor;

import java.util.List;
import java.util.stream.Collectors;

public class QueryResolver {

    public String resolve(final boolean arg) {
        return arg ? "TRUE" : "FALSE";
    }

    public String resolve(final int arg) {
        return String.valueOf(arg);
    }

    public String resolve(final List<Integer> arg) {
        if (arg == null || arg.isEmpty()) return "NONE";
        return arg.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    public String resolveString(final List<String> arg) {
        if (arg == null || arg.isEmpty()) return "NONE";
        return String.join(", ", arg);
    }

}
