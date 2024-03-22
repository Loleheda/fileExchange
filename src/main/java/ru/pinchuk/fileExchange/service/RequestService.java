package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;

public interface RequestService {
    Request getByRecipientAndFile(User currentUser, String username, String fileName);
    Request addRequest(User currentUser, String username, String fileName);
}
