package ru.pinchuk.fileExchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.FileRepository;
import ru.pinchuk.fileExchange.service.FileService;
import ru.pinchuk.fileExchange.service.MinioService;


@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final MinioService minioService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, MinioService minioService) {
        this.fileRepository = fileRepository;
        this.minioService = minioService;
    }

    @Override
    public File getFile(String name, User user) {
        return fileRepository.findByOwnerAndName(user, name);
    }

    @Override
    public byte[] downloadFile(String login, String fileName) {
        minioService.downloadObject(login, fileName);
        return minioService.downloadObject(login, fileName);
    }

    @Override
    @Transactional
    public File addFile(MultipartFile file, User user) {
        if (file == null) {
            throw new RuntimeException("Нет данного файла");
        }
        minioService.addObject(user, file);
        File newFile = new File(user, file.getOriginalFilename());
        fileRepository.save(newFile);
        System.out.println("Файл " + file.getOriginalFilename() + " добавлен");
        return newFile;
    }

    @Override
    @Transactional
    public Long deleteFile(String name, User user) {
        minioService.removeObject(user.getLogin(), name);
        Long id = fileRepository.removeByOwnerAndName(user, name);
        System.out.println("Файл " + name + " удален");
        return id;
    }
}
