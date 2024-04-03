package ru.pinchuk.fileExchange.component;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "minio.access.name=testAccessKey",
        "minio.access.secret=testSecretKey",
        "minio.url=http://localhost:9000"
})
class MinioClientConfigTest {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.access.name}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Autowired
    private ApplicationContext context;

    @Test
    void getMinioClientTest() {
        assertNotNull(minioClient);
        assertNotNull(context.getBean("getMinioClient"));
        assertEquals(accessKey, "testAccessKey");
        assertEquals(secretKey, "testSecretKey");
        assertEquals(minioUrl, "http://localhost:9000");
    }
}