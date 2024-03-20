package ru.pinchuk.fileExchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.RequestStatus;
import ru.pinchuk.fileExchange.service.RequestService;

@Controller
@RequestMapping("/{username}/{fileName}")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping()
    public String getRequest(@PathVariable String username, @PathVariable String fileName) {
        Request request = requestService.getRequestByRecipientAndFile(username, fileName);
        String status = request.getStatus().getName();
        switch (status) {
            case "Доступен":
                break;
            case "Заблокирован":
                break;
            case "В процессе":
                break;

            return "/request";
    }
}
