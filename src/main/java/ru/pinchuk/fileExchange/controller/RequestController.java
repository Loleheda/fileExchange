package ru.pinchuk.fileExchange.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.FileService;
import ru.pinchuk.fileExchange.service.MinioService;
import ru.pinchuk.fileExchange.service.RequestService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final FileService fileService;

    public RequestController(RequestService requestService, FileService fileService) {
        this.requestService = requestService;
        this.fileService = fileService;
    }

    @GetMapping("/{username}/{fileName}")
    public String showRequest(@PathVariable String username, @PathVariable String fileName, HttpSession http, Model model) {
        User currentUser = (User) http.getAttribute("user");
        Request request = requestService.getByRecipientAndFile(currentUser, username, fileName);
        return "/request";
    }

    @GetMapping("/{username}/{fileName}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String username, @PathVariable String fileName, HttpSession http) {
        

        byte[] data = fileService.downloadFile(username, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
