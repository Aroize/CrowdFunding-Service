package ru.ifmo.server.data.services;

import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        ArrayList<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<User> findAllByLogin(String login) {
        ArrayList<User> result = new ArrayList<>();
        userRepository.findAllByLogin(login).forEach(result::add);
        return result;
    }

    @Override
    public List<User> findById(int id) {
        ArrayList<User> result = new ArrayList<>();
        userRepository.findUserById(id).forEach(result::add);
        return result;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
