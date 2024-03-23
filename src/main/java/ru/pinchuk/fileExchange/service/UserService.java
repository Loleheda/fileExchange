package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface UserService {
    User addUser(String login, String password, String email);
    User addAdmin(String login, String password, String email);
    Long deleteByLogin(String login);
    User getByLogin(String login);
    User getByEmail(String email);
    List<User> getAllUser();
}
