package com.java_mail.spring_boot.controller;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("anand775803@gmail.com");
            message.setTo("yash77929@gmail.com");
            message.setSubject("Simple test email from Anand!");
            message.setText("This is a sample email body for my first email!");

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("anand775803@gmail.com");
            helper.setTo("yash77929@gmail.com");
            helper.setSubject("Java email with attachment | From Anand");
            helper.setText("Please find the attached documents below");

            helper.addAttachment("logo.png", new File("/Users/yashsingh/Downloads/logo.png"));
            helper.addAttachment("CV.docx", new File("/Users/yashsingh/Downloads/CV.docx"));

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-html-email")
    public String sendHtmlEmail() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("anand775803@gmail.com");
            helper.setTo("yash77929@gmail.com");
            helper.setSubject("Java email with attachment | From Anand");

            try (var inputStream = Objects.requireNonNull(EmailController.class.getResourceAsStream("/templates/email-content.html"))) {
                helper.setText(
                        new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                        true
                );
            }
            helper.addInline("logo.png", new File("/Users/yashsingh/Downloads/logo.png"));
            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}