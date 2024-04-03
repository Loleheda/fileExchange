package ru.pinchuk.fileExchange.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.FileService;
import ru.pinchuk.fileExchange.service.RequestService;
import ru.pinchuk.fileExchange.service.StatusService;
import ru.pinchuk.fileExchange.service.UserService;

import javax.servlet.http.HttpSession;
import java.net.URI;

@Controller
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final FileService fileService;
    private final UserService userService;
    private final StatusService statusService;

    public RequestController(RequestService requestService, FileService fileService, UserService userService, StatusService statusService) {
        this.requestService = requestService;
        this.fileService = fileService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/{username}/{fileName}")
    public String showRequest(@PathVariable String username, @PathVariable String fileName, HttpSession http, Model model) {
        User currentUser = (User) http.getAttribute("user");
        Request request = requestService.getByRecipientAndFile(currentUser, username, fileName);
        if (request.getStatus().getName().equals("AVAILABLE")) {
            return String.format("redirect:/request/%s/%s/download", username, fileName);
        }
        model.addAttribute("req", request);
        return "/request";
    }

    @GetMapping("/{username}/{fileName}/download")
    public ResponseEntity<Object> downloadFile(@PathVariable String username, @PathVariable String fileName) {
        byte[] data = fileService.downloadFile(username, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/{ownerName}/{recipientName}/{fileName}/isPermitted")
    public String downloadFilePermission(@PathVariable String ownerName, @PathVariable String recipientName, @PathVariable String fileName, HttpSession http, Model model) {
        User currentUser = (User) http.getAttribute("user");
        if (!ownerName.equals(currentUser.getLogin()))  {
            return "redirect:/files";
        }
        User recipient = userService.getByLogin(recipientName);
        Request request = requestService.getByRecipientAndFile(recipient, ownerName, fileName);
        // Необходимо, для корректный работы с названиями файлов на русском языке
        URI url = UriComponentsBuilder
                .fromUriString("/request/{ownerName}/{recipientName}/{fileName}/isPermitted")
                .build(ownerName, recipientName, fileName);
        model.addAttribute("url", url);
        http.setAttribute("req", request);
        model.addAttribute("req", request);
        return "/isPermitted";
    }

    @GetMapping("/{ownerName}/{recipientName}/{fileName}/isPermitted/yes")
    public String allowDownloadFile(@PathVariable String ownerName, @PathVariable String recipientName, @PathVariable String fileName, HttpSession http, Model model) {
        Request request = (Request) http.getAttribute("req");
        requestService.updateStatus(request, statusService.getByName("AVAILABLE"));
        http.removeAttribute("req");
        return "redirect:/files";
    }

    @GetMapping("/{ownerName}/{recipientName}/{fileName}/isPermitted/no")
    public String notAllowDownloadFile(@PathVariable String ownerName, @PathVariable String recipientName, @PathVariable String fileName, HttpSession http) {
        Request request = (Request) http.getAttribute("req");
        requestService.updateStatus(request, statusService.getByName("LOCKED"));
        http.removeAttribute("req");
        return "redirect:/files";
    }

}
