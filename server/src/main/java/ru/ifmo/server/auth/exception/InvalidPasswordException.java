package ru.ifmo.server.auth.exception;

public class InvalidPasswordException extends InvalidUserException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
