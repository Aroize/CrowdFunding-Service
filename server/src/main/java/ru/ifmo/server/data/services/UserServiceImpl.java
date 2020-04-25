package ru.ifmo.server.data.services;

import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.User;
import ru.ifmo.server.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public User findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
    }

    @Override
    public User findById(int id) {
        ArrayList<User> result = new ArrayList<>();
        userRepository.findUserById(id).forEach(result::add);
        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByIdForEdit(int id) {
        return userRepository.findByIdForEdit(id);
    }
}
