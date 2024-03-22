package ru.pinchuk.fileExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByEmail(String email);
    List<User> findUsersByRole(Role role);
    Long removeByLogin(String login);
}
