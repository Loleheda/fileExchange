package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.RequestStatus;

public interface StatusService {
    RequestStatus getByName(String name);
    RequestStatus addStatus(String name);
}
