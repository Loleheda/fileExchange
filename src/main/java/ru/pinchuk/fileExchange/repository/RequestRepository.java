package ru.pinchuk.fileExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findRequestByRecipientAndFile(User recipient, File file);
    Long deleteByFile(File file);
}
