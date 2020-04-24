package ru.ifmo.server.bills;

import org.hibernate.LockMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.UserService;

import javax.persistence.LockModeType;

@Component
public class BillServiceImpl implements BillService {

    public static int delay = 5000;

    public int getDelay() {
        int saved = delay;
        delay = 0;
        return saved;
    }

    private final UserService userService;

    public BillServiceImpl(UserService userService) {
        this.userService = userService;
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
    public void transactionToFund(int uid, int amount, int fundId) {
        //TODO(Create transaction to funds)
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
