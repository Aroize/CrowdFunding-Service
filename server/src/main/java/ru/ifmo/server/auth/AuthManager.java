package ru.ifmo.server.auth;

import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;

public interface AuthManager {
    User signUp(String login, String password) throws InvalidUserException;

    AccessToken signIn(String login, String password) throws InvalidUserException;
}
