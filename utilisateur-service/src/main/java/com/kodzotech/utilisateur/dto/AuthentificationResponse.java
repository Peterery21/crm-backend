package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthentificationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Long expiresAt;
    private String username;
    private String email;
    private String name;
    private Long utilisateurId;
    private Long societeId;
    private EntiteDto entite;
}
