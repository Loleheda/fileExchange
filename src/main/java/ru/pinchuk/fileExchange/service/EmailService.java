package ru.pinchuk.fileExchange.service;

import org.springframework.mail.SimpleMailMessage;
import ru.pinchuk.fileExchange.entity.Request;

public interface EmailService {
    void sendEmail(Request request);
}
