package ru.ifmo.server.auth;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.UserService;

import java.util.List;

@RestController
@Component
public class AuthController {

    private final Gson gson = new Gson();

    @Autowired
    private AuthManager authManager;

    @GetMapping("/auth.signUp")
    public ResponseEntity<String> signUp(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        User user = authManager.signUp(login, password);
        if (user == null) {
            return new ResponseEntity<>("{ \"error_code\" : 1, \"failure_msg\" : \"User with this login already exists\"  }", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(gson.toJson(user), HttpStatus.OK);
    }

    @GetMapping("/auth.signIn")
    public ResponseEntity<String> signIn(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        AccessToken accessToken = authManager.signIn(login, password);
        if (accessToken == null) {
            return new ResponseEntity<>("{ \"error_code\" : 2, \"failure_msg\" : \"Bad password or no such user\"  }", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(accessToken), HttpStatus.OK);
    }
}
