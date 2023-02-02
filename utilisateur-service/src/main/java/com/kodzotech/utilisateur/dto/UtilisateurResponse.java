package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Long societeId;
    private EntiteDto entite;
}
