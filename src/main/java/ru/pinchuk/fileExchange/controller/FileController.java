package ru.pinchuk.fileExchange.controller;

import io.minio.*;
import io.minio.messages.Item;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.pinchuk.fileExchange.component.MinioClientComponent;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.FileService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    public String getFiles(Model model, HttpSession http) {
        String login = ((User) http.getAttribute("user")).getLogin();
        MinioClient minioClient = MinioClientComponent.getMinioClient();
        Iterator<Result<Item>> iterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(login).build()).iterator();
        List<Result<Item>> files = new ArrayList<>();
        iterator.forEachRemaining(files::add);
        model.addAttribute("res", files);
        return "/files";
    }

    @GetMapping("/add")
    public String addFile() {
        return "/addFile";
    }

    @PostMapping("/add")
    public String addFile(@RequestParam("file") MultipartFile file, HttpSession http) {
        fileService.addFile(file, (User) http.getAttribute("user"));
        return "redirect:/files";
    }

    @GetMapping("/{fileName}/delete")
    public String deleteFile(@PathVariable String fileName, HttpSession http) {
        fileService.deleteFile(fileName, (User) http.getAttribute("user"));
        return "redirect:/files";
    }
}