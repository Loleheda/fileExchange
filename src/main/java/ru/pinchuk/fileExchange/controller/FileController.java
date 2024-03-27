package ru.pinchuk.fileExchange.controller;

import io.minio.*;
import io.minio.messages.Item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.FileService;
import ru.pinchuk.fileExchange.service.MinioService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final MinioService minioService;

    public FileController(FileService fileService, MinioService minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @GetMapping()
    public String getFiles(Model model, HttpSession http) {
        List<Result<Item>> files = minioService.getObjectsByUser((User) http.getAttribute("user"));
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