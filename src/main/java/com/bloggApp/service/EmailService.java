package com.bloggApp.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {

//    void sendEmailWithTemplate(String to, String subject, String imagePath) throws MessagingException, IOException;

    void sendEmail(String[] to, String subject, String message);
}
