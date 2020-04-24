package ru.ifmo.server.bills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.Fund;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.FundService;
import ru.ifmo.server.data.services.UserService;

@Component
public class BillServiceImpl implements BillService {

    private final FundService fundService;

    private final UserService userService;

    public BillServiceImpl(UserService userService, FundService fundService) {
        this.userService = userService;
        this.fundService = fundService;
    }

    @Override
    public void addAmount(int uid, int amount) throws InvalidUserException, InvalidBalanceException {
        User user = userService.findByIdForEdit(uid);
        if (user == null)
            throw new InvalidUserException("No such user");
        int balance = user.getBalance();
        if (balance + amount < 0)
            throw new InvalidBalanceException("Not enough money to perform operation");
        user.addAmount(amount);
        userService.save(user);
    }

    @Override
    @Transactional
    public void transactionToFund(int uid, int amount, int fundId) throws InvalidUserException, InvalidBalanceException {
        if (amount < 0) {
            throw new RuntimeException("Can't donate negative amount of money to fund");
        }
        addAmount(uid, -amount);
        Fund fund = fundService.findFundByIdForEdit(fundId);
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (fund == null) {
            throw new RuntimeException("No such fund");
        }
        if (fund.getLimit() != null && fund.getRaised() + amount > fund.getLimit()) {
            throw new RuntimeException("Limit of fund can't permit to store more money");
        }
        fund.addAmount(amount);
        fundService.save(fund);
    }

    @Override
    @Transactional(readOnly = true)
    public int balance(int uid) throws InvalidUserException {
        User user = userService.findById(uid);
        if (user == null)
            throw new InvalidUserException();
        return user.getBalance();
    }
}
