package ru.pinchuk.fileExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pinchuk.fileExchange.entity.RequestStatus;

public interface StatusRepository extends JpaRepository<RequestStatus, Long> {
    RequestStatus findRequestStatusByName(String name);
}
