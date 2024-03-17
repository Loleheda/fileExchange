package ru.pinchuk.fileExchange.controller;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.minio.MinioClientConnection;
import ru.pinchuk.fileExchange.service.UserService;

import java.io.IOException;
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
//        model.addAttribute("user", user);

        MinioClient minioClient = MinioClientConnection.getMinioClient();

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(user.getLogin()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(user.getLogin()).build());
        }

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(user.getLogin()).build());
        Iterator<Result<Item>> items = results.iterator();
        List<Result<Item>> res = new ArrayList<>();
        items.forEachRemaining(res::add);
        model.addAttribute("res", res);
        while (items.hasNext())
        {
            Result<Item> item = items.next();
            System.out.println(item.get().objectName());
        }
        System.out.println("Вы используете бакет " + user.getLogin());


        return "/files";
    }

//    @GetMapping("/{itemName}")
//    public String getItem() {
//
//    }
}