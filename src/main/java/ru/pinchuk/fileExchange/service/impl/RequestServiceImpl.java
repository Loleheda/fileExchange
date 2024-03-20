package ru.pinchuk.fileExchange.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.FileRepository;
import ru.pinchuk.fileExchange.repository.RequestRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;
import ru.pinchuk.fileExchange.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public RequestServiceImpl(RequestRepository requestRepository, FileRepository fileRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Request getRequestByRecipientAndFile(String username, String fileName) {
        User senderUser = userRepository.findByLogin(username);
        User currentUser = userRepository.findByLogin(authenticationUsername());
        File file = fileRepository.findByOwnerAndName(senderUser, fileName);
        Request request = requestRepository.findRequestByRecipientAndFile(currentUser, file);
        return request;
    }

    private String authenticationUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
