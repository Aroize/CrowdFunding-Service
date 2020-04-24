package ru.ifmo.server.data.services;

import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.Fund;
import ru.ifmo.server.data.repositories.FundRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class FundServiceImpl implements FundService {

    private final FundRepository fundRepository;

    public FundServiceImpl(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    @Override
    public Fund save(Fund fund) {
        return fundRepository.save(fund);
    }

    @Override
    public Fund findFundById(int id) {
        return fundRepository.findById(id).orElse(null);
    }

    @Override
    public Fund findFundByIdForEdit(int id) {
        return fundRepository.findFundByIdForEdit(id);
    }

    @Override
    public List<Fund> findFundsByOwner(int ownerId) {
        return new ArrayList<>(fundRepository.findFundByOwnerId(ownerId));
    }

    @Override
    public Fund findFundByName(String name) {
        return fundRepository.findFundByName(name).orElse(null);
    }
}