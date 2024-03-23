package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getRegistration() {
        return "/registration";
    }

    @PostMapping()
    public String createUser(String login, String password, String email) {
        if (login.length() < 3 || email.isEmpty() || password.isEmpty()) {
//            model.addAttribute("error", "Введите логин, почту, пароль");
            return "/registration";
        }
        if (userService.getByLogin(login) != null || userService.getByEmail(email) != null) {
//            model.addAttribute("error", "Пользователь с таким именем или почтой уже существует");
            return "/registration";
        }
        userService.addUser(login, password, email);
        return "redirect:/login";
    }
}