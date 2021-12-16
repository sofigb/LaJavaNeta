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

    @Value("garciariveros@@gmail.com")
    private String from;

    private static  String SUBJECT = "Correo de bienvenida";
    private static  String TEXT = "Bienvenido a la pagina web de Agus.";

    @Async
    public void send(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }

    @Async
    public void sendEspecialEmail(String to,String password,String name){
        SimpleMailMessage message = new SimpleMailMessage();
        TEXT="Bienvenido "+ name +" a la pagina web de virtual wallet. Su correo es " + to + " y su contraseña es "+ password;
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }

}
