package ru.pinchuk.fileExchange.service;

import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    Long deleteUser(User user);
    Long deleteUserById(Long id);
    User findByLogin(String login);
    User findByEmail(String email);
    User editUser(User user);
    List<User> getAll();
}
