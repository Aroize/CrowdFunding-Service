package ru.ifmo.server.auth.access;

import java.util.Optional;

public interface AccessTokenService {
    AccessToken findAccessTokenById(int userId);

    void deleteAccessToken(int userId);

    void save(AccessToken accessToken);

    Optional<AccessToken> findAccessTokenByValue(String tokenValue, int userId);
}
