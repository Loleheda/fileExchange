package ru.pinchuk.fileExchange.minio;

import io.minio.MinioClient;

public class MinioClientConnection {

    // Запуск в докере
    // docker run -p 9000:9000 -p 9001:9001 minio/minio server /data --console-address ":9001"

    private static MinioClient minioClient;

    public static synchronized MinioClient getMinioClient() {
        if (minioClient == null) {
            minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("Vof03VquVROxBpBrAz8z", "hRFPcg6OQOPg7T0VQFkjqnHtvayF3cphpX7gXydx")
                    .build();
        }
        return minioClient;
    }
}
