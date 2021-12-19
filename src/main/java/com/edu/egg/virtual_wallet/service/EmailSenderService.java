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


    @Value("${USERNAME}")
    private String from;

    private static  String SUBJECT = "Bienvenidx a AgroPay";
    private static  String TEXT = "Hola,nos alegra que te unas a esta familia.Ya hemos creado tu cuenta para que comiences a operar.";

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
