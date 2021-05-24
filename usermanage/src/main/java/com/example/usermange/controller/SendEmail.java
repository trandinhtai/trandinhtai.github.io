package com.example.usermange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendEmail {
    @Autowired
    public JavaMailSender emailSender;

    @GetMapping("/send-simple-email")
    public String sendSimpleEmail() {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("trandinhtai@gmail.com");
        message.setSubject("Test Simple Email");
        message.setText("Hello World");

        // Send Message!
        emailSender.send(message);

        return "Email Sent!";
    }
}