package com.goylik.cleverbank.service.exception;

/**
 * Custom exception class representing an error when an operation cannot be completed due to insufficient balance.
 * This exception is thrown when attempting to withdraw more funds than available in an account.
 */
public class InsufficientBalanceException extends ServiceException {
    public InsufficientBalanceException() {
        super();
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientBalanceException(Throwable cause) {
        super(cause);
    }
}
