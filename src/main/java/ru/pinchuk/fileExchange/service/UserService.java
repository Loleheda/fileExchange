package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface UserService {
    User addUser(String login, String password, String email);
    Long deleteUser(User user);
    Long deleteUserById(Long id);
    User getByLogin(String login);
    User getByEmail(String email);
    User editUser(User user);
    List<User> getAll();
}
