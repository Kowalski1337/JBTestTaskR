package exception;

public class ParseException extends Exception {
    public ParseException(String what) {
        super(what);
    }

    public ParseException(String what, int where) {
        super(what + " at position: " + Integer.toString(where));
    }
}
