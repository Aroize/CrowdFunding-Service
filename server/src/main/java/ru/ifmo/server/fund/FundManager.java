package ru.ifmo.server.fund;

import ru.ifmo.server.data.entities.Fund;

public interface FundManager {
    Fund create(Fund fund) throws IllegalFundException;

    Fund create(String name, int ownerId, Integer limit) throws IllegalFundException;
}
