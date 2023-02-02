package com.kodzotech.notification.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationDto {
    private String subject;
    private String to;
    private String senderIdentifiant;
    private String senderName;
    private String text;
    private List<Fichier> fichiers;
}
