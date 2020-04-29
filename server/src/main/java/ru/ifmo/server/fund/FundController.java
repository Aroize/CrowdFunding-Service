package ru.ifmo.server.fund;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.ResponseManager;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.data.entities.Fund;

import java.util.List;

@RestController
public class FundController {

    private final int DEFAULT_COUNT = 20;

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

    @GetMapping("/fund.get")
    public ResponseEntity<String> getFunds(
        @RequestParam(name = "count", required = false) Integer count,
        @RequestParam(name = "offset", required = false) Integer offset
    ) {
        if (count == null) {
            count = DEFAULT_COUNT;
        }
        if (count < 0) {
            return ResponseManager.createResponse(new IllegalArgumentException("Count must be positive"), 2);
        }
        if (offset == null) {
            offset = 0;
        }
        if (offset < 0) {
            return ResponseManager.createResponse(new IllegalArgumentException("Offset must be positive"), 3);
        }
        List<Fund> funds = fundManager.getFunds(count, offset);
        return ResponseManager.createResponse(funds);
    }
}
