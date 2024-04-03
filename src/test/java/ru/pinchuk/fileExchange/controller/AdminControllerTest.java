package ru.pinchuk.fileExchange.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @Test
    void showAdminPanelTest() {
        // Arrange
        Role role = new Role("USER");
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", "user1", "user1", role));
        userList.add(new User("user2", "user2", "user2", role));
        Mockito.when(userService.getAllUser()).thenReturn(userList);

        // Act
        String result = adminController.showAdminPanel(model);

        // Assert
        assertEquals("/adminPanel", result);
        Mockito.verify(model).addAttribute("users", userList);
    }

    @Test
    void deleteUserTest() {
        // Arrange
        String username = "test";
        User user = new User(1L, username, "test", "test", new Role("USER"));
        Mockito.when(userService.deleteByLogin(username)).thenReturn(1L);

        // Act
        String result = adminController.deleteUser(username);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(userService).deleteByLogin(username);
    }

    @Test
    void showAdminTest() {
        // Act
        String result = adminController.showAdmin();

        // Assert
        assertEquals("/registrationAdmin", result);
    }

    @Test
    void addAdminTest() {
        // Arrange
        String login = "newAdmin";
        String password = "admin123";
        String email = "admin@example.com";

        // Act
        String result = adminController.addAdmin(login, password, email);

        // Assert
        assertEquals("redirect:/admin", result);
        Mockito.verify(userService).addAdmin(login, password, email);
    }
}
