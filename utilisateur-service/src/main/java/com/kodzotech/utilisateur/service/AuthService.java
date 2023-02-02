package com.kodzotech.utilisateur.service;

import com.kodzotech.utilisateur.dto.AuthentificationResponse;
import com.kodzotech.utilisateur.dto.LoginRequest;
import com.kodzotech.utilisateur.dto.RefreshTokenRequest;
import com.kodzotech.utilisateur.dto.RegisterRequest;

public interface AuthService {
    /**
     * Enregistrement des informations de securite
     *
     * @param registerRequest
     */
    void signup(RegisterRequest registerRequest);

    /**
     * Méthode de connexion pour la sécurité
     *
     * @param loginRequest
     * @return
     */
    AuthentificationResponse login(LoginRequest loginRequest);

    /**
     * Méthode de rafraichissement du token
     *
     * @param refreshTokenRequest
     * @return
     */
    AuthentificationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    /**
     * Méthode de déconnexion
     *
     * @param refreshTokenRequest
     */
    void logout(RefreshTokenRequest refreshTokenRequest);
}
