package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.RequestStatus;
import ru.pinchuk.fileExchange.entity.User;

/**
 * Сервис для работы со запросами на скачивание файла
 */
public interface RequestService {

    /**
     * Получает запрос по получателю, имени отпрвителя и имени файла
     *
     * @param currentUser  получатель
     * @param username     имя отправителя
     * @param fileName     имя файла
     * @return {@link Request}
     */
    Request getByRecipientAndFile(User currentUser, String username, String fileName);

    /**
     * Создает запрос на получение файла
     *
     * @param currentUser  получатель
     * @param username     имя отправителя
     * @param fileName     имя файла
     * @return {@link Request}
     */
    Request addRequest(User currentUser, String username, String fileName);

    /**
     * Обновляет статус запроса
     *
     * @param request  запрос на скачивание
     * @param status   новый статус
     * @return {@link Request} запрос с обновленным статусом
     */
    Request updateStatus(Request request, RequestStatus status);
}
