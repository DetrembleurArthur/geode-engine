package com.geode.exceptions;

public class GeodeException extends RuntimeException {
    public GeodeException() {
    }

    public GeodeException(String message) {
        super(message);
    }

    public GeodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeodeException(Throwable cause) {
        super(cause);
    }

    public GeodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
