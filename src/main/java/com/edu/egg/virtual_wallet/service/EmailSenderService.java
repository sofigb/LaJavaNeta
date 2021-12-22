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

    @Value("AgroPayCompany@gmail.com")
    private String from;

    private static  String SUBJECT = "Le damos la bienvenida a AgroPay";
    private static  String SUBTEXT = ", nos alegra que te unas a esta familia.Ya hemos creado tu cuenta para que comiences a operar.";

    @Async
    public void send(String to,String name){
        SimpleMailMessage message = new SimpleMailMessage();
      String TEXT="Hola "+name+SUBTEXT;
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }

    @Async
    public void sendEspecialEmail(String to, String password, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText("Bienvenido a la empresa! Su username es " + username + " y su contraseña es "+ password);
        sender.send(message);
    }
}
