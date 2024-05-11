package com.fithub.services.auth.core.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {

    @Value("${email.sender.name}")
    private String emailSenderName;

    private final JavaMailSender javaMailSender;

    public EmailHelper(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPlaintextEmail(String subject, String recipientEmailAddress, String text) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailSenderName);
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setTo(recipientEmailAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }

}