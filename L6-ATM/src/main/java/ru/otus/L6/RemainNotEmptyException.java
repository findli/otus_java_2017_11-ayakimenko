package ru.otus.L6;

/**
 * Created by abyakimenko on 26.03.2018.
 */
public class RemainNotEmptyException extends RuntimeException {
    
    public RemainNotEmptyException() {
        super();
    }

    public RemainNotEmptyException(String message) {
        super(message);
    }

    public RemainNotEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemainNotEmptyException(Throwable cause) {
        super(cause);
    }

    protected RemainNotEmptyException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
