package ru.ifmo.server.bills;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.ResponseManager;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.auth.exception.InvalidUserException;


@RestController
@Component
public class BillController {

    private final BillService billService;

    private final AccessManager accessManager;

    public BillController(BillService billService, AccessManager accessManager) {
        this.billService = billService;
        this.accessManager = accessManager;
    }

    @GetMapping("/bill.userAddAmount")
    public ResponseEntity<String> userAddAmount(
            @RequestParam(name = "token") String tokenValue,
            @RequestParam int uid,
            @RequestParam int amount
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return ResponseManager.tokenExpiredResponse;
        }
        ResponseEntity<String> response;
        try {
            billService.addAmount(uid, amount);
            response = ResponseManager.simpleResponse;
        } catch (InvalidUserException e) {
            response = ResponseManager.createResponse(e, 2);
        } catch (InvalidBalanceException e) {
            response = ResponseManager.createResponse(e, 3);
        }
        return response;
    }

    @GetMapping("/bill.donate")
    public ResponseEntity<String> donate(
            @RequestParam(name = "token") String tokenValue,
            @RequestParam int uid,
            @RequestParam int amount,
            @RequestParam(name = "fund_id") int fundId
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return ResponseManager.tokenExpiredResponse;
        }
        ResponseEntity<String> response;
        try {
            billService.transactionToFund(uid, amount, fundId);
            response = ResponseManager.simpleResponse;
        } catch (InvalidUserException e) {
            response = ResponseManager.createResponse(e, 2);
        } catch (InvalidBalanceException e) {
            response = ResponseManager.createResponse(e, 3);
        } catch (RuntimeException e) {
            response = ResponseManager.createResponse(e, 4);
        }
        return response;
    }

    @GetMapping("/bill.getBalance")
    public ResponseEntity<String> getBalance(
            @RequestParam(name = "token") String tokenValue,
            @RequestParam int uid
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return ResponseManager.tokenExpiredResponse;
        }
        ResponseEntity<String> response;
        try {
            int balance = billService.balance(uid);
            response = ResponseManager.createResponse(balance);
        } catch (InvalidUserException e) {
            response = ResponseManager.createResponse(e, 2);
        }
        return response;
    }
    
}
