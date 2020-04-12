package ru.ifmo.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.UserService;

@Component
public class AuthManagerImpl implements AuthManager {

    @Autowired
    private UserService userService;

    @Autowired
    private AccessManager accessManager;

    @Override
    public User signUp(String login, String password) {
        User users = userService.findByLogin(login);
        if (users != null)
            return null;
        User user = new User();
        user.setLogin(login);
        user.setHashedPassword(password);
        userService.save(user);
        return user;
    }

    @Override
    public AccessToken signIn(String login, String password) {
        User user = userService.findByLogin(login);
        if (user == null || !user.getHashedPassword().equals(password)) {
            return null;
        }
        return accessManager.registerTokenForUser(user);
    }
}
