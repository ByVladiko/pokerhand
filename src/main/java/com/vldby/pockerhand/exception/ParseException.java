package com.vldby.pockerhand.exception;

public class ParseException extends Exception {

    private final int errorOffset;

    public ParseException(String s, int errorOffset) {
        super(s);
        this.errorOffset = errorOffset;
    }

    public ParseException(Exception exception, int errorOffset) {
        super(exception);
        this.errorOffset = errorOffset;
    }

    public ParseException(String s) {
        super(s);
        this.errorOffset = -1;
    }

    public ParseException(String message, int index, Throwable cause) {
        super(message, cause);
        this.errorOffset = index;
    }

    public int getErrorOffset() {
        return errorOffset;
    }
}
