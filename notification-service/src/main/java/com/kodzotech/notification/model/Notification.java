package com.kodzotech.notification.model;

import com.kodzotech.notification.dto.Fichier;
import lombok.Data;

import java.util.List;

@Data
public class Notification {
    private String subject;
    private String to;
    private String senderAdresse;
    private String senderName;
    private String text;
    private List<Fichier> fichiers;
}
