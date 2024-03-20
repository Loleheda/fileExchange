package ru.pinchuk.fileExchange.component;

import io.minio.MinioClient;
import org.springframework.stereotype.Component;

@Component
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
