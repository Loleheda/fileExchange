package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pinchuk.fileExchange.service.UserService;

/**
 * Контроллер регистрации пользователей
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу регистрации.
     * @return представление страницы регистрации
     */
    @GetMapping()
    public String getRegistration() {
        return "/registration";
    }

    /**
     * Создает нового пользователя
     * @param error флаг ошибки регистрации
     * @param login логин нового пользователя
     * @param password пароль нового пользователя
     * @param email электронная почта нового пользователя
     * @param model объект Model для передачи данных в представление
     * @return перенаправление на страницу входа в случае успешной регистрации, иначе представление страницы регистрации с сообщениями об ошибках
     */
    @PostMapping()
    public String createUser(@RequestParam(required = false) Boolean error, String login, String password, String email, Model model) {
        if (login.length() < 3) {
            model.addAttribute("errorLoginLengthMessage", "Логин  должен быть больше 3 символов");
            error = true;
        }
        if (userService.getByLogin(login) != null) {
            model.addAttribute("errorLoginExistMessage", "Пользователь с таким именем уже существует");
            error = true;
        }
        if (userService.getByEmail(email) != null) {
            model.addAttribute("errorEmailExistMessage", "Пользователь с такой почтой уже существует");
            error = true;
        }
        if (password.length() < 3) {
            model.addAttribute("errorPasswordLengthMessage", "Пароль  должен быть больше 3 символов");
            error = true;
        }
        if (error != null && error) {
            return "registration";
        }
        userService.addUser(login, password, email);
        return "redirect:/login";
    }
}