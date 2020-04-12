package ru.ifmo.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping("/auth.register")
    public String auth(@RequestParam(name = "login") String login, @RequestParam(name = "hashed_pwd") String password) {
        List<User> result = userService.findAll();
        final StringBuilder builder = new StringBuilder();
        result.forEach(user -> builder.append(user.getLogin()).append('\n'));
        builder.append("size : ").append(result.size());
        return builder.toString();
    }

}
