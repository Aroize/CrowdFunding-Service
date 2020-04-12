package ru.ifmo.server.auth;

public interface AccessTokenService {
    AccessToken findAccessTokenById(int userId);

    void deleteAccessToken(int userId);

    void save(AccessToken accessToken);
}
