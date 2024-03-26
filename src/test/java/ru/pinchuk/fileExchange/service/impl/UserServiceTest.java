package ru.pinchuk.fileExchange.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.RoleRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.MinioService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private MinioServiceImpl minioService;
    @InjectMocks
    private UserServiceImpl userService;

    private String login;
    private String password;
    private String email;

    @BeforeEach
    void setUp() {
        login = "1234567890";
        password = "1234567890";
        email = "1234567890";
    }

    @Test
    public void addUserTest() {
        User user = userService.addUser(login, password, email);
        Assertions.assertEquals(user, new User(login, passwordEncoder.encode(password), email, roleRepository.findRoleByName("USER")));
    }

    @Test
    public void addAdminTest() {
        User user = userService.addAdmin(login, password, email);
        Assertions.assertEquals(user, new User(login, passwordEncoder.encode(password), email, roleRepository.findRoleByName("ADMIN")));
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        user.setLogin(login);

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleService, passwordEncoder, minioService);
        userService.deleteByLogin(user.getLogin());

        Mockito.verify(userRepository, Mockito.times(1)).removeByLogin(user.getLogin());
    }

}
