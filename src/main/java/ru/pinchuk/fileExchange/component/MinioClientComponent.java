package ru.pinchuk.fileExchange.component;

import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//@Component
public class MinioClientComponent {
    private static MinioClient minioClient;

    public static synchronized MinioClient getMinioClient() {
        if (minioClient == null) {
            minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("MINIOADMIN", "MINIOADMIN")
                    .build();
        }
        return minioClient;
    }
}
