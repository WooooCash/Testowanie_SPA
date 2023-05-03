package ats.v1.query.validator;

public class QueryException extends RuntimeException {

    public QueryException(String message) {
        super("Error while parsing query: " + message);
    }

}
