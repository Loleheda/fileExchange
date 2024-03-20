package ru.pinchuk.fileExchange.service;

import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface FileService {
    List<File> getFilesByUser(User user);
    File getFileByName(String name);
    File addFile(MultipartFile file);
    Long deleteFile(String name);
}
