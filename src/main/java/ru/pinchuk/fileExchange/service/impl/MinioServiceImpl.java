package ru.pinchuk.fileExchange.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.MinioService;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void addBucket(User user) {
        addBucket(user.getLogin());
    }

    @Override
    public void addBucket(String bucket) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addObject(User user, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(user.getLogin()).object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void removeObject(String login, String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(login).object(fileName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void removeObjects(String bucket) {
        List<Result<Item>> files = getObjectsByUser(bucket);
        for (int i = 0; i < files.size(); i++) {
            try {
                removeObject(bucket, files.get(i).get().objectName());
            } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public byte[] downloadObject(String bucket, String fileName)  {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build());
            byte[] content = IOUtils.toByteArray(stream);
            stream.close();
            return content;
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void removeBucket(String bucket) {
        try {
            removeObjects(bucket);
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Result<Item>> getObjectsByUser(User user) {
        return getObjectsByUser(user.getLogin());
    }

    @Override
    public List<Result<Item>> getObjectsByUser(String login) {
        Iterator<Result<Item>> iterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(login).build()).iterator();
        List<Result<Item>> files = new ArrayList<>();
        iterator.forEachRemaining(files::add);
        return files;
    }
}
