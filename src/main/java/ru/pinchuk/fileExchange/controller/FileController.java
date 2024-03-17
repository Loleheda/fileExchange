package ru.pinchuk.fileExchange.controller;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.minio.MinioClientConnection;
import ru.pinchuk.fileExchange.service.UserService;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final UserService userService;

    public FileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getFiles(Model model) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(authentication.getName());

        MinioClient minioClient = MinioClientConnection.getMinioClient();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(user.getLogin()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(user.getLogin()).build());
        }
        Iterator<Result<Item>> iterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(user.getLogin()).build()).iterator();
        List<Result<Item>> files = new ArrayList<>();
        iterator.forEachRemaining(files::add);

        model.addAttribute("res", files);
        return "/files";
    }

    @GetMapping("/{fileName}/delete")
    public String deleteFile(@PathVariable String fileName, Model model) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient = MinioClientConnection.getMinioClient();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(username).object(fileName).build());

        return "redirect:/files";
    }

    @GetMapping("/add")
    public String getItem() {
        return "/addFile";
    }

    @PostMapping("/add")
    public String addItem(@RequestParam("file") MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (file == null) {
            throw new RuntimeException("Нет данного файла");
        }

        MinioClient minioClient = MinioClientConnection.getMinioClient();
        minioClient.putObject(
                PutObjectArgs.builder().bucket(username).object(file.getOriginalFilename()).stream(
                                file.getInputStream(), file.getSize(), -1)
                        .contentType("video/mp4")
                        .build());

        return "redirect:/files";
    }
}