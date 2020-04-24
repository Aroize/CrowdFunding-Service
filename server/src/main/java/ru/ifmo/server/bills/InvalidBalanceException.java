package ru.ifmo.server.bills;

import ru.ifmo.server.auth.exception.InvalidUserException;

public class InvalidBalanceException extends Exception {
    public InvalidBalanceException() {
        super();
    }

    public InvalidBalanceException(String message) {
        super(message);
    }
}
