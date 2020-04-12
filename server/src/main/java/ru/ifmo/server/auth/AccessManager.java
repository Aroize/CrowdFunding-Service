package ru.ifmo.server.auth;

import ru.ifmo.server.data.entities.User;

public interface AccessManager {
    AccessToken registerTokenForUser(User user);
}
