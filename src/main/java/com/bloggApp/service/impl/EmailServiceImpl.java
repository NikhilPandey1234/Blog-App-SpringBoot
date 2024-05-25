package com.bloggApp.service.impl;

import com.bloggApp.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("nikhilpanday46@gmail.com");
        javaMailSender.send(simpleMailMessage);
        logger.info("Email has been sent..!");
    }

   /* @Override
    public void sendEmailWithTemplate(String to, String subject, String imagePath) throws MessagingException, IOException {
        String htmlBody = Files.readString(Path.of("/home/root412/Music/BlogAppSpringBoo/src/main/resources/email-template.html"));

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        // Add the image as an inline attachment
        FileSystemResource image = new FileSystemResource(imagePath);
        helper.addInline("image1", image);
        helper.setFrom("nikhilpanday46@gmail.com");

        javaMailSender.send(mimeMessage);
        logger.info("Email with template has been sent..!");
    }*/


}
