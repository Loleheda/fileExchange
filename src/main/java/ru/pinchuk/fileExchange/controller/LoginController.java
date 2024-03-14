package ru.pinchuk.fileExchange.controller;

import org.bouncycastle.crypto.PasswordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.UserService;
import ru.pinchuk.fileExchange.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("login")
public class LoginController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping()
    public String postLogin(String login, String pass) {
        User user = userService.findByLogin(login);
//        System.out.println(user);
//        System.out.println(pass);
//        System.out.println(passwordEncoder.encode(pass));
//        System.out.println(user.getPassword() + " " + pass);
//        System.out.println(user.getPassword().equals(/*passwordEncoder.encode(*/pass));
//        System.out.println(user == null || !user.getPassword().equals());
        System.out.println(passwordEncoder.encode(pass));
        System.out.println(passwordEncoder.matches(pass, user.getPassword()));
        if (user == null || !passwordEncoder.matches(pass, user.getPassword())) {
            System.out.println(1222);
            return "redirect:/login";
        }
        return "redirect:/files";
    }
}