package ru.pinchuk.fileExchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.pinchuk.fileExchange.entity.Request;
import ru.pinchuk.fileExchange.entity.User;
import ru.pinchuk.fileExchange.service.EmailService;

import java.net.URI;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("spring.mail.username")
    String fromEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(Request request) {
        User owner = request.getFile().getOwner();
        String to = owner.getEmail();
        String subject = "Разрешение на скачивание файла " + request.getFile().getName();
        URI url = UriComponentsBuilder
                .fromUriString("http://localhost:8080/request/{owner}/{recipient}/{fileName}/isPermitted")
                .build(owner.getLogin(), request.getRecipient().getLogin(), request.getFile().getName());
        String text = String.format(
                "Здравствуте! Пользователь %s отправил Вам запрос на скачивание файла %s. Перейдите по ссылке %s, чтобы разреешить скачивание",
                request.getRecipient().getLogin(),
                request.getFile().getName(),
                url);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}