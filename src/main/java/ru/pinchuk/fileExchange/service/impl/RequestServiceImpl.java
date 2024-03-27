package ru.pinchuk.fileExchange.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.RequestStatus;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.RequestRepository;
import ru.pinchuk.fileExchange.service.*;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final FileService fileService;
    private final UserService userService;
    private final StatusService statusService;
    private final MinioService minioService;
    private final EmailService emailService;

    public RequestServiceImpl(RequestRepository requestRepository, FileService fileService, UserService userService,
                              StatusService statusService, MinioService minioService, EmailService emailService) {
        this.requestRepository = requestRepository;
        this.fileService = fileService;
        this.userService = userService;
        this.statusService = statusService;
        this.minioService = minioService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public Request getByRecipientAndFile(User recipient, String username, String fileName) {
        User owner = userService.getByLogin(username);
        File file = fileService.getFile(fileName, owner);
        Request request = requestRepository.findRequestByRecipientAndFile(recipient, file);
        if (request == null) {
            request = addRequest(recipient, username, fileName);
            requestRepository.save(request);
            emailService.sendEmail(request);
        } else if (request.getStatus().getName().equals("Доступен")) {
            minioService.downloadObject(owner.getLogin(), fileName);
        }
        return request;
    }

    @Override
    public Request addRequest(User recipient, String username, String fileName) {
        User owner = userService.getByLogin(username);
        File file = fileService.getFile(fileName, owner);
        if (file == null) {
            return null;
        }
        RequestStatus status = statusService.getByName("Отправлен отправителю");
        Request request = new Request(recipient, file, status);
        requestRepository.save(request);
        System.out.println("Запрос на файл " + fileName + " пользователя " + username + " создан");
        return request;
    }

    /**
     * @param request
     * @param status
     * @return
     */
    @Override
    public Request updateStatus(Request request, RequestStatus status) {
        request.setStatus(status);
        return requestRepository.save(request);
    }
}
