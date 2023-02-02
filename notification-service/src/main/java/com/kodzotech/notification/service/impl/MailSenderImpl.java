package com.kodzotech.notification.service.impl;

import com.kodzotech.notification.dto.Fichier;
import com.kodzotech.notification.dto.NotificationDto;
import com.kodzotech.notification.service.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;


@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithAttachment(NotificationDto notificationDto) {

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(msg, true);
            helper.setFrom(new InternetAddress(notificationDto.getSenderIdentifiant(),
                    notificationDto.getSenderName()));
            helper.setTo(notificationDto.getTo());
            helper.setSubject(notificationDto.getSubject());
            helper.setText(notificationDto.getText(), true);
            if (notificationDto.getFichiers() != null) {
                for (Fichier fichier : notificationDto.getFichiers()) {
                    URL url = new URL(fichier.getFileUrl());
                    helper.addAttachment(fichier.getFilename(), new URLDataSource(url));
                }
            }
            javaMailSender.send(msg);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }
}
