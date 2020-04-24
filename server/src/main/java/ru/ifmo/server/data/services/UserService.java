package ru.ifmo.server.data.services;

import ru.ifmo.server.data.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByLogin(String login);

    User findById(int id);

    void save(User user);

    User findByIdForEdit(int id);
}
