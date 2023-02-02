package com.kodzotech.utilisateur.service.impl;

import com.kodzotech.utilisateur.security.JwtProvider;
import com.kodzotech.utilisateur.service.AuthService;
import com.kodzotech.utilisateur.service.RefreshTokenService;
import com.kodzotech.utilisateur.service.UtilisateurService;
import com.kodzotech.utilisateur.dto.*;
import com.kodzotech.utilisateur.utils.TresosoftConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SecurityService securityService;
    private final RefreshTokenService refreshTokenService;
    private final UtilisateurService utilisateurService;
    private final JwtProvider jwtProvider;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Override
    public void signup(RegisterRequest registerRequest) {
        securityService.signup(registerRequest);
    }

    @Override
    public AuthentificationResponse login(LoginRequest loginRequest) {
        //Vérification de l'authentification et récuperation du token
        AuthentificationResponse authentificationResponse = securityService.login(loginRequest);
        //Récupération des informations de l'utilisateur
        UtilisateurResponse utilisateur = utilisateurService
                .getUtilisateurByUsername(authentificationResponse.getUsername());
        authentificationResponse
                .setAuthenticationToken(generateToken(authentificationResponse.getUsername(),
                        authentificationResponse.getAuthenticationToken(), utilisateur));
        authentificationResponse.setSocieteId(utilisateur.getSocieteId());
        authentificationResponse.setEntite(utilisateur.getEntite());
        authentificationResponse.setUtilisateurId(utilisateur.getId());
        return authentificationResponse;
    }

    @Override
    public AuthentificationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        AuthentificationResponse authentificationResponse = securityService.refreshToken(refreshTokenRequest);
        //Récupération des informations de l'utilisateur
        UtilisateurResponse utilisateur = utilisateurService
                .getUtilisateurByUsername(authentificationResponse.getUsername());
        authentificationResponse
                .setAuthenticationToken(generateToken(authentificationResponse.getUsername(),
                        authentificationResponse.getAuthenticationToken(), utilisateur));
        authentificationResponse.setSocieteId(utilisateur.getSocieteId());
        authentificationResponse.setEntite(utilisateur.getEntite());
        authentificationResponse.setUtilisateurId(utilisateur.getId());
        return authentificationResponse;
    }

    @Override
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    }

    private String generateToken(String username, String token, UtilisateurResponse utilisateur) {
        //Contruction du map
        Map<String, Object> map = new HashMap<>();
        map.put(TresosoftConstant.SOCIETE_ID, utilisateur.getSocieteId());
        if (utilisateur.getEntite() != null && utilisateur.getEntite().getId() != null) {
            map.put(TresosoftConstant.ENTITE_ID, utilisateur.getEntite().getId());
        }
        map.put(TresosoftConstant.UTILISATEUR_ID, utilisateur.getId());
        //jwtProvider.setJwtExpirationInMillis(jwtExpirationInMillis);
        String newToken = jwtProvider
                .addDataToToken(token,
                        map);
        return newToken;
    }
}
