package ru.ifmo.server.data.services;

import ru.ifmo.server.data.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findAllByLogin(String login);

    List<User> findById(int id);

    void save(User user);
}
