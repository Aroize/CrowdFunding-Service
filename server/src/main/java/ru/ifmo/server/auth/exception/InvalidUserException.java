package ru.ifmo.server.auth.exception;

public class InvalidUserException extends Exception {
    public InvalidUserException() {
        super();
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
