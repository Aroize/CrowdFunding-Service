package ru.ifmo.server.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.ResponseManager;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.data.entities.Fund;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.UserService;
import ru.ifmo.server.fund.FundManager;

import java.util.List;

@RestController
public class UserController {

    private final FundManager fundManager;

    private final UserService userService;

    private final AccessManager accessManager;

    public UserController(AccessManager accessManager, UserService userService, FundManager fundManager) {
        this.accessManager = accessManager;
        this.userService = userService;
        this.fundManager = fundManager;
    }

    @GetMapping("/user.get")
    public ResponseEntity<String> get(
            @RequestParam("token") String tokenValue,
            @RequestParam int uid
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return ResponseManager.tokenExpiredResponse;
        }
        User user = userService.findById(uid);
        if (user == null) {
            return ResponseManager.createResponse(
                    new IllegalArgumentException("No such user"),
                    2
            );
        }
        return ResponseManager.createResponse(user);
    }

    @GetMapping("/user.funds")
    public ResponseEntity<String> funds(
            @RequestParam("token") String tokenValue,
            @RequestParam int uid
    ) {
        if (!accessManager.checkAccessToken(tokenValue, uid)) {
            return ResponseManager.tokenExpiredResponse;
        }
        List<Fund> userFunds = fundManager.findFundsByOwner(uid);
        return ResponseManager.createResponse(userFunds);
    }
}
