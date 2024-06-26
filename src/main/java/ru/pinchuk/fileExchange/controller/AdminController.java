package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pinchuk.fileExchange.service.UserService;

/**
 * Контроллер для управления панелью администратора
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Показывает панель администратора
     * @param model объект Model
     * @return представление панели администратора
     */
    @GetMapping()
    public String showAdminPanel(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "/adminPanel";
    }

    /**
     * Удаляет пользователя по его имени
     * @param username имя пользователя
     * @return перенаправление на панель администратора
     */
    @GetMapping("/user/{username}/delete")
    public String deleteUser(@PathVariable String username) {
        Long id = userService.deleteByLogin(username);
        System.out.println("Пользователь с id: " + id + " удален");
        return "redirect:/admin";
    }

    /**
     * Отображает форму для добавления администратора
     * @return представление формы добавления администратора
     */
    @GetMapping("/add")
    public String showAdmin() {
        return "/registrationAdmin";
    }

    /**
     * Добавляет нового администратора
     * @param login    логин администратора
     * @param password пароль администратора
     * @param email    электронная администратора
     * @return перенаправление на панель администратора в случае успешной регистрации, иначе представление страницы регистрации с сообщениями об ошибках
     */
    @PostMapping("/add")
    public String addAdmin(@RequestParam(required = false) Boolean error, String login, String password, String email, Model model) {
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
            return "/registrationAdmin";
        }
        userService.addAdmin(login, password, email);
        return "redirect:/admin";
    }
}