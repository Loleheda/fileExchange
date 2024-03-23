package ru.pinchuk.fileExchange.component;

import io.minio.MinioClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MinioClientConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("MINIOADMIN", "MINIOADMIN")
                .build();
    }
}
