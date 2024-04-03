package ru.pinchuk.fileExchange.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.UserService;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    @Test
    void showLoginErrorWhenErrorIsTrueTest() {
        String errorMessage = "Не верный логин или пароль";
        Mockito.when(model.addAttribute("errorMessage", errorMessage)).thenReturn(null);
        String result = loginController.showLoginError(true, model);
        assertEquals("/login", result);
    }

    @Test
    void showLoginErrorWhenErrorIsNullTest() {
        String result = loginController.showLoginError(null, model);
        assertEquals("/login", result);
    }

    @Test
    void distributionForAdminUserTest() {
        // Arrange
        User admin = new User();
        admin.setLogin("admin");
        admin.setRole(new Role("ADMIN"));

        Mockito.when(userService.getByLogin(anyString())).thenReturn(admin);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(admin.getLogin());
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String result = loginController.distribution(httpSession);

        // Assert
        assertEquals("redirect:/admin", result);
    }

    @Test
    void distributionForRegularUserTest() {
        // Arrange
        User admin = new User();
        admin.setLogin("user");
        admin.setRole(new Role("USER"));

        Mockito.when(userService.getByLogin(anyString())).thenReturn(admin);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(admin.getLogin());
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String result = loginController.distribution(httpSession);

        // Assert
        assertEquals("redirect:/files", result);
    }
}