package ru.ifmo.server.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.server.data.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    Iterable<User> findUserById(int id);

    @Query(value="SELECT * FROM users WHERE id = :uid FOR UPDATE", nativeQuery=true)
    User findByIdForEdit(@Param("uid") int id);
}
