package ru.ifmo.server.bills;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.auth.exception.InvalidUserException;


@RestController
@Component
public class BillController {

    private static final String ERROR_CODE = "\"error code\" : ";
    private static final String FAILURE_MSG = "\"failure_msg\" : ";

    private final static ResponseEntity<String> TOKEN_EXPIRED_RESPONSE =
            new ResponseEntity<>("{ \"error_code\" : -1, \"failure_msg\" : \"Access token has expired or invalid\" }", HttpStatus.UNAUTHORIZED);

    private final static String BODY_OK = "{ \"response\" : %d }";

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
            return TOKEN_EXPIRED_RESPONSE;
        }
        String response;
        HttpStatus status;
        try {
            billService.addAmount(uid, amount);
            response = String.format(BODY_OK, 1);
            status = HttpStatus.OK;
        } catch (InvalidUserException e) {
            response = "{\"error_code\" : 2, \"failure_msg\" : \"" + e.getMessage() + "\"}";
            status = HttpStatus.BAD_REQUEST;
        } catch (InvalidBalanceException e) {
            response = "{\"error_code\" : 3, \"failure_msg\" : \"" + e.getMessage() + "\"}";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/bill.donate")
    public ResponseEntity<String> donate(
            @RequestParam(name = "token") String tokenValue,
            @RequestParam int uid,
            @RequestParam int amount,
            @RequestParam(name = "fund_id") int fundId
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return TOKEN_EXPIRED_RESPONSE;
        }
        String response;
        HttpStatus status;
        try {
            billService.transactionToFund(uid, amount, fundId);
            response = String.format(BODY_OK, 1);
            status = HttpStatus.OK;
        } catch (InvalidUserException e) {
            response = "{ " + ERROR_CODE + 2 + ", " + FAILURE_MSG + e.getMessage() + " }";
            status = HttpStatus.BAD_REQUEST;
        } catch (InvalidBalanceException e) {
            response = "{ " + ERROR_CODE + 3 + ", " + FAILURE_MSG + e.getMessage() + " }";
            status = HttpStatus.BAD_REQUEST;
        } catch (RuntimeException e) {
            response = "{ " + ERROR_CODE + 4 + ", " + FAILURE_MSG + e.getMessage() + " }";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/bill.getBalance")
    public ResponseEntity<String> getBalance(
            @RequestParam(name = "token") String tokenValue,
            @RequestParam int uid
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return TOKEN_EXPIRED_RESPONSE;
        }
        String response;
        HttpStatus status;
        try {
            int balance = billService.balance(uid);
            response = String.format(BODY_OK, balance);
            status = HttpStatus.OK;
        } catch (InvalidUserException e) {
            response = "{\"error_code\" : 2, \"failure_msg\" : \"No such user\"}";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
    
}
