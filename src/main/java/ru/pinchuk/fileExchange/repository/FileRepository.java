package ru.pinchuk.fileExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByOwnerAndName(User owner, String name);
    Long removeByOwnerAndName(User owner, String name);
}
