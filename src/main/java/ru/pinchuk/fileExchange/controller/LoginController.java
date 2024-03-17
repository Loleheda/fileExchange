package ru.pinchuk.fileExchange.controller;

import org.bouncycastle.crypto.PasswordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.Collection;


@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String getLogin() {
        return "/login";
    }
}