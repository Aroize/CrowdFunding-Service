package ru.ifmo.server.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, Integer> {
    Optional<Object> findByUserId(int userId);
}
