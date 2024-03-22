package ru.pinchuk.fileExchange.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.component.MinioClientComponent;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.repository.RoleRepository;
import ru.pinchuk.fileExchange.repository.UserRepository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements ru.pinchuk.fileExchange.service.UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(String login, String password, String email) {
        User newUser =  new User(login, passwordEncoder.encode(password), email, roleRepository.findRoleByName("USER"));
        try {
            MinioClient minioClient = MinioClientComponent.getMinioClient();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(newUser.getLogin()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(newUser.getLogin()).build());
            }
            userRepository.save(newUser);
            System.out.println("Пользователь " + newUser.getLogin() + " создан");
        } catch (XmlParserException | ErrorResponseException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException |
                 ServerException e) {
            System.out.println(e.getMessage());
        }
        return newUser;
    }

    @Override
    public Long deleteByLogin(String login) {
        MinioClient minioClient = MinioClientComponent.getMinioClient();
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(login).build());
            return userRepository.removeByLogin(login);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findUsersByRole(roleRepository.findRoleByName("USER"));
    }
}
