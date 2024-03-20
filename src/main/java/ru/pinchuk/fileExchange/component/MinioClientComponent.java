package ru.pinchuk.fileExchange.component;

import io.minio.MinioClient;
import org.springframework.stereotype.Component;

@Component
public class MinioClientComponent {

    // Запуск в докере
    // docker run \
    //   -p 9000:9000 \
    //   -p 9001:9001 \
    //   --name minio1 \
    //   -v D:\minio\data:/data \
    //   -e "MINIO_ROOT_USER=ROOTUSER" \
    //   -e "MINIO_ROOT_PASSWORD=CHANGEME123" \
    //   quay.io/minio/minio server /data --console-address ":9001"

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
