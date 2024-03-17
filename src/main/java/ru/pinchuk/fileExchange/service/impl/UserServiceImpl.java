package ru.pinchuk.fileExchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.RoleRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements ru.pinchuk.fileExchange.service.UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User addUser(User user) {
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setRole(roleRepository.findRoleByName("USER"));
        User newUser = userRepository.save(user);
        System.out.println(newUser);
        return newUser;
    }

    @Override
    public Long deleteUser(User user) {
        userRepository.delete(user);
        return user.getId();
    }

    @Override
    public Long deleteUserById(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User editUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
