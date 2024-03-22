package ru.pinchuk.fileExchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String getLogin() {
        return "/login";
    }

    @GetMapping("/distribution")
    public String distribution(HttpSession http) {
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        http.setAttribute("user", user);
        if (user.getRole().getName().equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/files";
    }

}