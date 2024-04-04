package ru.pinchuk.fileExchange.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MinioServiceImplTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioServiceImpl minioService;

    @Test
    void addBucketTest() throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        // Arrange
        String bucketName = "TEST";
        when(minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())).thenReturn(false);

        // Act
        minioService.addBucket(bucketName);

        // Assert
        verify(minioClient).makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    @Test
    void addObjectTest() throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        // Arrange
        User user = new User("test", "password", "test@gmail.com", new Role("USER"));
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("testFile.txt");
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream("fileContent".getBytes()));
        when(mockFile.getSize()).thenReturn(11L);
        when(mockFile.getContentType()).thenReturn("text/plain");

        // Act
        minioService.addObject(user, mockFile);

        // Assert
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }
}