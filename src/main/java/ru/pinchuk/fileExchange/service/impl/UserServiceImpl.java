package ru.pinchuk.fileExchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.MinioService;
import ru.pinchuk.fileExchange.service.RoleService;

import java.util.List;

/**
 * Сервис взаимодействия с пользователями
 * */
@Service
public class UserServiceImpl implements ru.pinchuk.fileExchange.service.UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MinioService minioService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder, MinioService minioService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.minioService = minioService;
    }

    @Override
    public User addUser(String login, String password, String email) {
        User newUser =  new User(login, passwordEncoder.encode(password), email, roleService.getByName("USER"));
        minioService.addBucket(newUser);
        userRepository.save(newUser);
        System.out.println("Пользователь " + newUser.getLogin() + " создан");
        return newUser;
    }

    @Override
    public User addAdmin(String login, String password, String email) {
        User newUser =  new User(login, passwordEncoder.encode(password), email, roleService.getByName("ADMIN"));
        userRepository.save(newUser);
        System.out.println("Пользователь " + newUser.getLogin() + " создан");
        return newUser;
    }

    @Override
    @Transactional
    public Long deleteByLogin(String login) {
        minioService.removeBucket(login);
        return userRepository.removeByLogin(login);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findUsersByRole(roleService.getByName("USER"));
    }
}
