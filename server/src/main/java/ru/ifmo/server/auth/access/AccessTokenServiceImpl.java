package ru.ifmo.server.auth.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.server.auth.access.AccessToken;
import ru.ifmo.server.auth.access.AccessTokenRepository;
import ru.ifmo.server.auth.access.AccessTokenService;

import java.util.Optional;

@Component
public class AccessTokenServiceImpl implements AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public AccessTokenServiceImpl(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

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

    @Override
    public Optional<AccessToken> findAccessTokenByValue(String tokenValue, int userId) {
        return accessTokenRepository.findAccessTokenByAccessTokenAndUserId(tokenValue, userId);
    }
}
