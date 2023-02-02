package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Long societeId;
    private Long entiteId;
}
