package ru.pinchuk.fileExchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;
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

        assertEquals("/files",  view);
    }


    @Test
    void addFileTest() {
        // Arrange
        MultipartFile multipartFile = null; // Для теста файл не нужен
        User user = new User("test","test","test",new Role("USER"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        // Act
        String viewName = fileController.addFile(multipartFile, session);

        // Assert
        assertEquals("redirect:/files", viewName);
        verify(fileService, times(1)).addFile(multipartFile, user);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    void deleteFileTest() {
        // Arrange
        String fileName = "testFile";
        User user = new User("test","test","test",new Role("USER"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        // Act
        String viewName = fileController.deleteFile(fileName, session);

        // Assert
        assertEquals("redirect:/files", viewName);
        verify(fileService, times(1)).deleteFile(fileName, user);
        verifyNoMoreInteractions(fileService);
    }
}
