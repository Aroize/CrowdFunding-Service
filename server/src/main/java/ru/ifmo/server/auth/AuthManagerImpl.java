package ru.ifmo.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.server.auth.access.AccessManager;
import ru.ifmo.server.auth.access.AccessToken;
import ru.ifmo.server.auth.exception.InvalidPasswordException;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.services.UserService;

@Component
public class AuthManagerImpl implements AuthManager {

    private final UserService userService;

    private final AccessManager accessManager;

    public AuthManagerImpl(UserService userService, AccessManager accessManager) {
        this.userService = userService;
        this.accessManager = accessManager;
    }

    @Override
    public User signUp(String login, String password) throws InvalidUserException {
        User users = userService.findByLogin(login);
        if (users != null)
            throw new InvalidUserException();
        User user = new User();
        user.setLogin(login);
        user.setHashedPassword(password);
        userService.save(user);
        return user;
    }

    @Override
    public AccessToken signIn(String login, String password) throws InvalidUserException {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new InvalidUserException();
        }
        if (!user.getHashedPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
        return accessManager.registerTokenForUser(user);
    }

    @Override
    public void logout(String accessToken, int usedId) {
        accessManager.deleteTokenForUser(accessToken, usedId);
    }
}
