package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.RequestStatus;

/**
 * Сервис для работы со статусами запросов на скачивание файлов
 */
public interface StatusService {

    /**
     * Получает статус по его имени
     *
     * @param name имя статуса
     * @return {@link RequestStatus}
     */
    RequestStatus getByName(String name);
}
