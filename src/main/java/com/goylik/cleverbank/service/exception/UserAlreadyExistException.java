package com.goylik.cleverbank.service.exception;

/**
 * This exception is thrown when an attempt is made to create a user that already exists in the system.
 */
public class UserAlreadyExistException extends ServiceException {
    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
