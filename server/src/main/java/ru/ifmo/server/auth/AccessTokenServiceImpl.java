package ru.ifmo.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Override
    public AccessToken findAccessTokenById(int userId) {
        return accessTokenRepository.findById(userId).orElse(null);
    }

    @Override
    public void deleteAccessToken(int userId) {
        accessTokenRepository.findByUserId(userId)
                .ifPresent(previousAccessToken -> {
                    int id = ((AccessToken)previousAccessToken).getId();
                    accessTokenRepository.deleteById(id);
                });
    }

    @Override
    public void save(AccessToken accessToken) {
        accessTokenRepository.save(accessToken);
    }
}
