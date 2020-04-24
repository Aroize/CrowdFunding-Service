package ru.ifmo.server.fund;

import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.Fund;
import ru.ifmo.server.data.services.FundService;

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
}
