package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("files")
public class FileController {

//    private final UserRepository userRepository;
//
//    public LoginController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @GetMapping()
    public String getLogin(Model model) {
        return "files";
    }

    @PostMapping()
    public String postRegistration(Model model) {
        return "files";
    }
}