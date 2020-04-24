package ru.ifmo.server.data.services;

import ru.ifmo.server.data.entities.Fund;

import java.util.List;

public interface FundService {
    Fund save(Fund fund);

    Fund findFundById(int id);

    Fund findFundByIdForEdit(int id);

    List<Fund> findFundsByOwner(int ownerId);

    Fund findFundByName(String name);
}
