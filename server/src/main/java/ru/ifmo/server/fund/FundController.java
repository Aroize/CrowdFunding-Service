package ru.ifmo.server.fund;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.data.entities.Fund;

@RestController
public class FundController {

    private final static ResponseEntity<String> TOKEN_EXPIRED_RESPONSE =
            new ResponseEntity<>("{ \"error_code\" : -1, \"failure_msg\" : \"Access token has expired or invalid\" }", HttpStatus.UNAUTHORIZED);

    private final AccessManager accessManager;

    private final FundManager fundManager;

    public FundController(AccessManager accessManager, FundManager fundManager) {
        this.accessManager = accessManager;
        this.fundManager = fundManager;
    }

    @GetMapping("/fund.create")
    public ResponseEntity<String> create(
            @RequestParam("token") String tokenValue,
            @RequestParam int uid,
            @RequestParam("fund_name") String fundName,
            @RequestParam(required = false) Integer limit
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return TOKEN_EXPIRED_RESPONSE;
        }
        String response;
        HttpStatus status;
        try {
            final Fund fund = fundManager.create(fundName, uid, limit);
            response = "{ \"obj\" : \"" + fund + "\" }";
            status = HttpStatus.OK;
        } catch (IllegalFundException e) {
            response = "BAD";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(response, status);
    }

    @GetMapping("/fund.getRaised")
    public ResponseEntity<String> getRaised(
        @RequestParam(name = "fund_id", required = false) Integer fundId,
        @RequestParam(name = "fund_name", required = false) String fundName
    ) {
        ResponseEntity<String> response;
        if (fundId == null && fundName == null) {
            return ResponseManager.createResponse(new IllegalArgumentException("Must be specified one of this parameters: fund_id or fund_name"), -2);
        }
        try {
            int raised;
            if (fundId != null) {
                raised = fundManager.raisedMoney(fundId);
            } else {
                raised = fundManager.raisedMoney(fundName);
            }
            response = ResponseManager.createResponse(raised);
        } catch (IllegalFundException e) {
            response = ResponseManager.createResponse(e, 2);
        }
        return response;
    }
}
