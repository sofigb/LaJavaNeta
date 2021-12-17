package com.edu.egg.virtual_wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender sender;

    @Value("sgonzalezbodello@gmail.com")
    private String from;

    private static final String SUBJECT = "Correo de bienvenida";
    private static final String TEXT = "Bienvenido a la pagina web de Agus.";

    @Async
    public void send(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }
}
