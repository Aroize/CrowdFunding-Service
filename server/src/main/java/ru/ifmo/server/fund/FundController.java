package ru.ifmo.server.fund;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.ResponseManager;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.data.entities.Fund;

@RestController
public class FundController {

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
            return ResponseManager.tokenExpiredResponse;
        }
        ResponseEntity<String> response;
        try {
            final Fund fund = fundManager.create(fundName, uid, limit);
            response = ResponseManager.createResponse(fund);
        } catch (IllegalFundException e) {
            response = ResponseManager.createResponse(e, 2);
        }
        return response;
    }
}
