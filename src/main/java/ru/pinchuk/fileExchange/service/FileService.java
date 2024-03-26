package ru.pinchuk.fileExchange.service;

import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

public interface FileService {
    List<File> getFilesByOwner(User user);
    File getFile(String name, User user);
    byte[] downloadFile(String login, String fileName);
    File addFile(MultipartFile file, User user);
    Long deleteFile(String name, User user);
}
