package com.kodzotech.notification.controller;

import com.kodzotech.notification.dto.NotificationDto;
import com.kodzotech.notification.service.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final MailSender mailSender;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMail(@RequestBody NotificationDto notificationDto) {
        mailSender.sendEmailWithAttachment(notificationDto);
    }

}
