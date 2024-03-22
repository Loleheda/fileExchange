package ru.pinchuk.fileExchange.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.component.MinioClientComponent;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.FileRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.FileService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<File> getFilesByOwner(User user) {
        return fileRepository.findFilesByOwner(user);
    }

    @Override
    public File getFile(String name, User user) {
        return fileRepository.findByOwnerAndName(user, name);
    }

    @Override
    @Transactional
    public String addFile(MultipartFile file, User user) {
        if (file == null) {
            throw new RuntimeException("Нет данного файла");
        }
        MinioClient minioClient = MinioClientComponent.getMinioClient();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(user.getLogin()).object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            File newFile = new File(user, file.getOriginalFilename());
            fileRepository.save(newFile);
            System.out.println("Файл " + file.getOriginalFilename() + " добавлен");
            return newFile.getName();
        } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public Long deleteFile(String name, User user) {
        MinioClient minioClient = MinioClientComponent.getMinioClient();
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(user.getLogin()).object(name).build());
            Long id = fileRepository.deleteByOwnerAndName(user, name);
            System.out.println("Файл " + name + " удален");
            return id;
        } catch (XmlParserException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                 ServerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
