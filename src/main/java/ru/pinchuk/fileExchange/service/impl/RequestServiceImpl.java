package ru.pinchuk.fileExchange.service.impl;

import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ServerSideEncryptionCustomerKey;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pinchuk.fileExchange.component.MinioClientComponent;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.RequestStatus;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.FileRepository;
import ru.pinchuk.fileExchange.repository.RequestRepository;
import ru.pinchuk.fileExchange.repository.StatusRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.RequestService;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    public RequestServiceImpl(RequestRepository requestRepository, FileRepository fileRepository, UserRepository userRepository,
                              StatusRepository statusRepository) {
        this.requestRepository = requestRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public Request getByRecipientAndFile(User recipient, String username, String fileName) {
        User owner = userRepository.findByLogin(username);
        File file = fileRepository.findByOwnerAndName(owner, fileName);
        Request request = requestRepository.findRequestByRecipientAndFile(recipient, file);
        if (request == null) {
            request = addRequest(recipient, username, fileName);
            requestRepository.save(request);
        }
        if (request.getStatus().getName().equals("Заблокирован")) {
            return null;
        }
        if (request.getStatus().getName().equals("Доступен")) {
            MinioClient minioClient = MinioClientComponent.getMinioClient();
            try {
                minioClient.downloadObject(
                        DownloadObjectArgs.builder()
                                .bucket(owner.getLogin())
                                .object(fileName)
                                .filename(owner.getLogin() + " " + fileName)
                                .build());
            } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                     InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new RuntimeException(e);
            }
        }
        return request;
    }

    @Override
    public Request addRequest(User recipient, String username, String fileName) {
        User owner = userRepository.findByLogin(username);
        File file = fileRepository.findByOwnerAndName(owner, fileName);
        if (file == null) {
            return null;
        }
        RequestStatus status = statusRepository.findByName("Отправлен отправителю");
        Request request = new Request(recipient, file, status);
        requestRepository.save(request);
        System.out.println("Запрос на файл " + fileName + " пользователя " + username + " создан");
        return request;
    }
}
