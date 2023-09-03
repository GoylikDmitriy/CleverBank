package com.goylik.cleverbank.service.exception;

/**
 * Custom exception class representing an error when an incorrect password is provided.
 */
public class IncorrectPasswordException extends ServiceException {
    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }
}
