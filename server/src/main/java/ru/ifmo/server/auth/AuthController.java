package ru.ifmo.server.auth;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.ResponseManager;
import ru.ifmo.server.auth.access.AccessToken;
import ru.ifmo.server.auth.exception.InvalidPasswordException;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;

@RestController
@Component
public class AuthController {

    private final AuthManager authManager;

    public AuthController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/auth.signUp")
    public ResponseEntity<String> signUp(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        ResponseEntity<String> response;
        try {
            User user = authManager.signUp(login, password);
            response = ResponseManager.createResponse(user);
        } catch (InvalidUserException e) {
            response = ResponseManager.createResponse(e, 2);
        }
        return response;
    }

    @GetMapping("/auth.signIn")
    public ResponseEntity<String> signIn(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        ResponseEntity<String> response;
        try {
            AccessToken accessToken = authManager.signIn(login, password);
            response = ResponseManager.createResponse(accessToken);
        } catch (InvalidPasswordException e) {
            response = ResponseManager.createResponse(e, 2);
        } catch (InvalidUserException e) {
            response = ResponseManager.createResponse(e, 3);
        }
        return response;
    }

    @GetMapping("/auth.logout")
    public ResponseEntity<String> logout(@RequestParam(name = "token") String token, @RequestParam(name = "uid") int uid) {
        authManager.logout(token, uid);
        return ResponseManager.simpleResponse;
    }
}
