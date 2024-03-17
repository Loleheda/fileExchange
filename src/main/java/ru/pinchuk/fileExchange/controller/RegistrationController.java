package ru.pinchuk.fileExchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.RoleService;
import ru.pinchuk.fileExchange.service.UserService;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getRegistration(Model model) {
        model.addAttribute("error", "");
        return "/registration";
    }

    @PostMapping()
    public String createUser(String login, String email, String pass, Model model) {

        String password = passwordEncoder.encode(pass);
        if (userService.findByLogin(login) != null || userService.findByEmail(email) != null) {
            model.addAttribute("error", "Пользователь с таким именем или почтой уже существует");
            return "redirect:/registration";
        }
        userService.addUser(new User(login, password, email, roleService.getRoleByName("USER")));
        return "redirect:/login";
    }
}