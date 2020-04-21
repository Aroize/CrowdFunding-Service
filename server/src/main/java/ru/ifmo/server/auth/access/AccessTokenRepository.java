package ru.ifmo.server.auth.access;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.server.auth.access.AccessToken;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, Integer> {
    Optional<Object> findByUserId(int userId);

    Optional<AccessToken> findAccessTokenByAccessTokenAndUserId(String accessToken, int userId);
}
