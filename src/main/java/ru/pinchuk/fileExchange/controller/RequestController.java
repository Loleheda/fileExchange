package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.RequestService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{username}/{fileName}")
    public String showRequest(@PathVariable String username, @PathVariable String fileName, HttpSession http) {
        User currentUser = (User) http.getAttribute("user");
        Request request = requestService.getByRecipientAndFile(currentUser, username, fileName);
        return "/request";
    }
}
