package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pinchuk.fileExchange.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAdminPanel(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "/adminPanel";
    }

    @DeleteMapping("/user/{username}/delete")
    public String deleteUser(@PathVariable String username) {
        Long id = userService.deleteByLogin(username);
        System.out.println("Пользователь с id: " + id + " удален");
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String showAdmin() {
        return "/registrationAdmin";
    }

    @PostMapping("/add")
    public String addAdmin(String login, String password, String email) {
        userService.addAdmin(login, password, email);
        return "redirect:/admin";
    }
}