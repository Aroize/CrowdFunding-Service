package ru.ifmo.server.bills;

import ru.ifmo.server.auth.exception.InvalidUserException;

public interface BillService {
    void addAmount(int uid, int amount) throws InvalidUserException, InvalidBalanceException;

    void transactionToFund(int uid, int amount, int fundId);

    int balance(int uid) throws InvalidUserException;
}
