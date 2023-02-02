package com.kodzotech.notification.service;

import com.kodzotech.notification.dto.NotificationDto;

public interface MailSender {


    void sendEmailWithAttachment(NotificationDto notificationDto);
}
