package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.Request;

/**
 * Сервис для отправки электронной почты
 */
public interface EmailService {

    /**
     * Отправляет запрос на подтверждение по электронной почте
     *
     * @param request запрос на подтверждение загрузки
     */
    void sendEmail(Request request);
}
