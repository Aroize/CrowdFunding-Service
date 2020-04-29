package ru.ifmo.server.fund;

import org.springframework.stereotype.Component;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.Fund;
import ru.ifmo.server.data.services.FundService;

import java.util.List;

@Component
public class FundManagerImpl implements FundManager {

    private final FundService fundService;

    public FundManagerImpl(FundService fundService) {
        this.fundService = fundService;
    }

    @Override
    public Fund create(Fund fund) throws IllegalFundException {
        Fund exists = fundService.findFundByName(fund.getName());
        if (exists != null) {
            throw new IllegalFundException("Fund already exists");
        }
        return fundService.save(fund);
    }

    @Override
    public Fund create(String name, int ownerId, Integer limit) throws IllegalFundException {
        final Fund fund = new Fund();
        fund.setOwnerId(ownerId);
        fund.setLimit(limit);
        fund.setName(name);
        return create(fund);
    }

    @Override
    public int raisedMoney(int fundId) throws IllegalFundException {
        final Fund fund = fundService.findFundById(fundId);
        return getFundRaisedMoney(fund);
    }

    @Override
    public int raisedMoney(String fundName) throws IllegalFundException {
        final Fund fund = fundService.findFundByName(fundName);
        return getFundRaisedMoney(fund);
    }

    @Override
    public List<Fund> findFundsByOwner(int id) {
        return fundService.findFundsByOwner(id);
    }

    @Override
    public List<Fund> getFunds(int count) {
        return fundService.getFunds(count);
    }

    private int getFundRaisedMoney(Fund fund) throws IllegalFundException {
        if (fund == null) {
            throw new IllegalFundException("No such fund");
        }
        return fund.getRaised();
    }
}
