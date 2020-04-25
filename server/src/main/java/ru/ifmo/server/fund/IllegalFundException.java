package ru.ifmo.server.fund;

public class IllegalFundException extends Exception {

    public IllegalFundException() {
        super();
    }

    public IllegalFundException(String message) {
        super(message);
    }
}
