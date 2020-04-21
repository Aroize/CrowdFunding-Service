package ru.ifmo.server.auth;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.auth.access.AccessToken;
import ru.ifmo.server.auth.exception.InvalidPasswordException;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;

@RestController
@Component
public class AuthController {

    private final Gson gson = new Gson();

    private final AuthManager authManager;

    public AuthController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/auth.signUp")
    public ResponseEntity<String> signUp(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        String responseBody;
        HttpStatus status;
        try {
            User user = authManager.signUp(login, password);
            responseBody = gson.toJson(user);
            status = HttpStatus.OK;
        } catch (InvalidUserException e) {
            responseBody = "{ \"error_code\" : 1, \"failure_msg\" : \"User with this login already exists\"  }";
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(responseBody, status);
    }

    @GetMapping("/auth.signIn")
    public ResponseEntity<String> signIn(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        String responseBody;
        HttpStatus status;
        try {
            AccessToken accessToken = authManager.signIn(login, password);
            responseBody = gson.toJson(accessToken);
            status = HttpStatus.OK;
        } catch (InvalidPasswordException e) {
            responseBody = "{\"error_code\" : 1, \"failure_msg\" : \"Password is incorrect\"}";
            status = HttpStatus.BAD_REQUEST;
        } catch (InvalidUserException e) {
            responseBody = "{\"error_code\" : 2, \"failure_msg\" : \"No such user\"}";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseBody, status);
    }

    @GetMapping("/auth.logout")
    public ResponseEntity<String> logout(@RequestParam(name = "token") String token, @RequestParam(name = "uid") int uid) {
        authManager.logout(token, uid);
        return new ResponseEntity<>("{ \"response\" : 1 }", HttpStatus.OK);
    }
}
