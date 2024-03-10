package ru.pinchuk.fileExchange.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("registration")
public class RegistrationController {

    @GetMapping()
    public String getRegistration(Model model) {
        return "registration";
    }

    @PostMapping()
    public String postRegistration(Model  model) {
        return null;
    }
}