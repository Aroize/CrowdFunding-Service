package ru.ifmo.server.auth.access;

import ru.ifmo.server.data.entities.User;

public interface AccessManager {
    AccessToken registerTokenForUser(User user);

    boolean checkAccessToken(String token, int uid);

    void deleteTokenForUser(String token, int uid);
}
