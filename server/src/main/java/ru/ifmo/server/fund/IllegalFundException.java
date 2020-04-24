package ru.ifmo.server.fund;

public class IllegalFundException extends Throwable {

    public IllegalFundException() {
        super();
    }

    public IllegalFundException(String message) {
        super(message);
    }
}
