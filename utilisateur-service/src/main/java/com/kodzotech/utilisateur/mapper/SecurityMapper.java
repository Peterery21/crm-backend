package com.kodzotech.utilisateur.mapper;

import com.kodzotech.utilisateur.dto.RegisterRequest;
import com.kodzotech.utilisateur.dto.UtilisateurDto;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public RegisterRequest mapDtoToRegisterRequest(UtilisateurDto utilisateurDto) {
        return RegisterRequest.builder()
                .email(utilisateurDto.getEmail())
                .username(utilisateurDto.getUsername())
                .password(utilisateurDto.getPassword())
                .enabled(utilisateurDto.isEnabled())
                .name(utilisateurDto.getNom() + " "
                        + (utilisateurDto.getPrenom() != null ? utilisateurDto.getPrenom() : ""))
                .build();
    }
}
