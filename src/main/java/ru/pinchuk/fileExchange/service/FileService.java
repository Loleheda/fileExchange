package ru.pinchuk.fileExchange.service;

import org.springframework.web.multipart.MultipartFile;
import ru.pinchuk.fileExchange.entity.File;
import ru.pinchuk.fileExchange.entity.User;

/**
 * Сервис для работы с файлами.
 */
public interface FileService {

    /**
     * Получает файл с заданным именем для указанного пользователя
     *
     * @param name имя файла
     * @param user пользователь, для которого осуществляется поиск файла
     * @return {@link File} новый файл
     */
    File getFile(String name, User user);

    /**
     * Скачивает файл по имени пользователя и названию файла.
     *
     * @param login    логин пользователя
     * @param fileName название файла
     * @return {@link Byte[]} массив байтов, представляющий содержимое файла
     */
    byte[] downloadFile(String login, String fileName);

    /**
     * Добавляет файл
     *
     * @param file загружаемый файл
     * @param user пользователь, для которого добавляется файл
     * @return {@link File} объект добавленного файла
     */
    File addFile(MultipartFile file, User user);

    /**
     * Удаляет файл
     *
     * @param name имя файла для удаления
     * @param user пользователь, файл которого удаляют
     * @return {@link Long} id файла
     */
    Long deleteFile(String name, User user);
}
