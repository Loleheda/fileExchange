package ru.pinchuk.fileExchange.service;

import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

/**
 * Сервис для взаимодействия с Minio Client.
 */
public interface MinioService {

    /**
     * Создает bucket для указанного пользователя
     *
     * @param user  пользователь, для которого создается bucket
     */
    void addBucket(User user);

    /**
     * Создает bucket для указанного логина пользователя
     *
     * @param login  логин пользователя, для которого создается bucket
     */
    void addBucket(String login);

    /**
     * Удаляет bucket
     *
     * @param bucket  название bucket для удаления
     */
    void removeBucket(String bucket);

    /**
     * Добавляет объект в bucket для указанного пользователя
     *
     * @param user  пользователь, для которого добавляется объект
     * @param file  загружаемый файл
     */
    void addObject(User user, MultipartFile file);

    /**
     * Удаляет объект из bucket для указанного пользователя
     *
     * @param login     логин пользователя
     * @param fileName  название удаляемого файла
     */
    void removeObject(String login, String fileName);

    /**
     * Удаляет все объекты из указанного bucket
     *
     * @param bucket  название bucket, из которого удаляются объекты
     */
    void removeObjects(String bucket);

    /**
     * Скачивает объект из bucket
     *
     * @param bucket    название bucket
     * @param fileName  название скачиваемого файла
     * @return  {@link Byte[]} массив байтов, представляющий содержимое скачиваемого файла
     */
    byte[] downloadObject(String bucket, String fileName);

    /**
     * Получает список файлов пользователя
     *
     * @param user  пользователь, для которого осуществляется поиск объектов
     * @return {@link Result<Item>} список файлов
     */
    List<Result<Item>> getObjectsByUser(User user);

    /**
     * Получает список объектов в bucket по логину пользователя
     *
     * @param login  логин пользователя, для которого осуществляется поиск объектов
     * @return {@link Result<Item>} список объектов
     */
    List<Result<Item>> getObjectsByUser(String login);
}
