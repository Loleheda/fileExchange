package ru.pinchuk.fileExchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getLogin() {
        return "/login";
    }

    @GetMapping("/distribution")
    public String distribution(HttpSession http) {
        User user = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        http.setAttribute("user", user);
        if (user.getRole().getName().equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/files";
    }

}