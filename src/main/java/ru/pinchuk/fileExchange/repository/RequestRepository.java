package ru.pinchuk.fileExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findRequestByRecipient(User recipient);
}
