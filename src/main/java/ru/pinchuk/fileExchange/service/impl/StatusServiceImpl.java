package ru.pinchuk.fileExchange.service.impl;

import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.RequestStatus;
import ru.pinchuk.fileExchange.repository.StatusRepository;
import ru.pinchuk.fileExchange.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public RequestStatus getByName(String name) {
        RequestStatus status = statusRepository.findByName(name);
        if (status == null) {
            throw new RuntimeException("Status not found: " + name);
        }
        return status;
    }
}
