package ru.ifmo.server.fund;

import ru.ifmo.server.data.entities.Fund;

import java.util.List;

public interface FundManager {
    Fund create(Fund fund) throws IllegalFundException;

    Fund create(String name, int ownerId, Integer limit) throws IllegalFundException;

    int raisedMoney(int fundId) throws IllegalFundException;

    int raisedMoney(String fundName) throws IllegalFundException;

    List<Fund> findFundsByOwner(int id);

    List<Fund> getFunds(int count);
}
