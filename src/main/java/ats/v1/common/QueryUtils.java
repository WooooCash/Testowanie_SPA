package ats.v1.common;

public class QueryUtils {

    public static boolean isDigit(char c) {
        try{//TODO do sprawdzenia , ew. poprawy
            return c >= '0' && c <= '9';
        }
        catch (final Exception e) {
            return false;
        }
    }

    public static boolean isAlpha(char c) {
        try { //TODO jw.
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }
        catch (final Exception e) {
            return false;
        }
    }
}
