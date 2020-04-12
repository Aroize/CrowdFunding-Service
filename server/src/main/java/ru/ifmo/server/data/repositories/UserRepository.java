package ru.ifmo.server.data.repositories;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.server.data.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Iterable<User> findAllByLogin(String login);

    Iterable<User> findUserById(int id);

}
