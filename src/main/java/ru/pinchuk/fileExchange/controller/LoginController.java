package ru.pinchuk.fileExchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * Контроллер входа пользователей
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу входа с сообщением об ошибке, если оно есть.
     *
     * @param error флаг наличия ошибки входа
     * @param model объект Model
     * @return представление страницы входа
     */
    @GetMapping
    public String showLogin(@RequestParam(required = false) Boolean error, Model model) {
        if (error != null && error) {
            model.addAttribute("errorMessage", "Не верный логин или пароль");
        }
        return "/login";
    }

    /**
     * Выполняет распределение пользователей на страницы в зависимости от их роли.
     *
     * @param http объект HttpSession
     * @return перенаправление на страницу администратора или файлов пользователя в зависимости от роли пользователя
     */
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