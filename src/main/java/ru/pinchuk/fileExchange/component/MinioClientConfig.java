package ru.pinchuk.fileExchange.component;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {
    @Value("${minio.access.name}")
    String accessKey;

    @Value("${minio.access.secret}")
    String secretKey;

    @Value("${minio.url}")
    String minioUrl;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }
}
