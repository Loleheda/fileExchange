package ru.pinchuk.fileExchange.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.RoleRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.impl.MinioServiceImpl;
import ru.pinchuk.fileExchange.service.impl.RoleServiceImpl;
import ru.pinchuk.fileExchange.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    @Mock
    private UserRepository userRepository;

    @Autowired
    @Mock
    private RoleRepository roleRepository;

    @Autowired
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private MinioServiceImpl minioService;
    @Mock
    private RoleServiceImpl roleService;
    @InjectMocks
    private UserServiceImpl userService;

//    @Mock
//    private Role role;

    @Test
    public void createUserTest() {
        String login = "1234567890";
        String password = "1234567890";
        String email = "1234567890";
        User user = userService.addUser(login, password, email);
        Assertions.assertEquals(user, new User(login, password, email, roleService.getRoleByName("USER")));
    }
}
