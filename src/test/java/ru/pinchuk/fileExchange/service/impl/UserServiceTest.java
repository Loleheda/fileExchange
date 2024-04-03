package ru.pinchuk.fileExchange.service.impl;

import org.junit.BeforeClass;
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

    // Arrange
    @BeforeEach
    void setUp() {
        login = "1234567890";
        password = "1234567890";
        email = "1234567890";
    }

    @Test
    public void addUserTest() {
        // Act
        User user = userService.addUser(login, password, email);
        // Assert
        Assertions.assertEquals(user, new User(login, passwordEncoder.encode(password), email, roleRepository.findByName("USER")));
    }

    @Test
    public void addAdminTest() {
        // Act
        User user = userService.addAdmin(login, password, email);
        // Assert
        Assertions.assertEquals(user, new User(login, passwordEncoder.encode(password), email, roleRepository.findByName("ADMIN")));
    }

    @Test
    public void deleteUserTest() {
        // Arrange
        User user = new User();
        user.setLogin(login);
        // Act
        userService.deleteByLogin(user.getLogin());
        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).removeByLogin(user.getLogin());
    }

}
