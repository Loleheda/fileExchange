package ru.pinchuk.fileExchange.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.FileService;
import ru.pinchuk.fileExchange.service.MinioService;
import ru.pinchuk.fileExchange.service.impl.FileServiceImpl;
import ru.pinchuk.fileExchange.service.impl.MinioServiceImpl;
import ru.pinchuk.fileExchange.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @Autowired
    private UserServiceImpl userService;

    @Mock
    private FileServiceImpl fileService;
    @Mock
    private MinioServiceImpl minioService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        fileService = mock(FileServiceImpl.class);
        minioService = mock(MinioServiceImpl.class);
        fileController = new FileController(fileService, minioService);
    }

    @Test
    public void getFilesTest() {
        String login = "USER_TEST";
        String password = "PASSWORD";
        String email = "EMAIL";
        Role role = new Role("ROLE_TEST");
        User user = new User(login, password, email, role);
        Model model = new ConcurrentModel();
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        String view = fileController.getFiles(model, httpSession);

        Assertions.assertEquals("/files",  view);
    }


}
