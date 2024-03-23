package ru.pinchuk.fileExchange.service;

import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface MinioService {
    void addBucket(User user);
    void addBucket(String login);
    void removeBucket(String bucket);
    void addObject(User user, MultipartFile file);
    void removeObject(User user, File file);
    void removeObject(String login, String fileName);
    void removeObjects(String bucket);
    void downloadObject(User user, File file);
    void downloadObject(String bucket, String fileName);
    List<Result<Item>> getObjectsByUser(User user);
    List<Result<Item>> getObjectsByUser(String login);
}
