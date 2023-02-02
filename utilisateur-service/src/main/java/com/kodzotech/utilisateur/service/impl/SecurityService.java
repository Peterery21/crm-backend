package com.kodzotech.utilisateur.service.impl;

import com.kodzotech.utilisateur.repository.UserRepository;
import com.kodzotech.utilisateur.repository.VerificationTokenRepository;
import com.kodzotech.utilisateur.dto.*;
import com.kodzotech.utilisateur.security.JwtProvider;
import com.kodzotech.utilisateur.service.RefreshTokenService;
import com.kodzotech.utilisateur.model.User;
import com.kodzotech.utilisateur.model.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authentificationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setCreated(Instant.now());
        user.setEnabled(true);

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new SecurityException("erreur.security.username.doublon");
        }

        userRepository.save(user);

        String token = generateVerificationToken(user);
//        mailService.sendMail(new NotificationEmail("Please activate your Account", user.getEmail(),
//                "Thank you for signing up to spring Reddit, " +
//                        "please click on the below url to activate your account : " +
//                        "http://localhost:8080/api/auth/accountVerification/" + token));
//        System.out.println("http://localhost:8080/api/auth/accountVerification/" + token);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SecurityException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new SecurityException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthentificationResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authentificationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new SecurityException("Informations incorrectes !!!");
        } catch (DisabledException ex) {
            throw new SecurityException("Utilisateur désactivé !!!");
        } catch (InternalAuthenticationServiceException ex) {
            throw new SecurityException("Informations incorrectes !!!");
        } catch (Exception ex) {
            throw new SecurityException(ex.getMessage());
        }
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
                new SecurityException("User not found with name - " + loginRequest.getUsername()));
        if (!user.isEnabled()) throw new SecurityException("Utilisateur désactivé");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthentificationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()).toEpochMilli())
                .username(loginRequest.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .build();

    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user found for username " + principal.getUsername()));
        return user;
    }

    public AuthentificationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthentificationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()).toEpochMilli())
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Transactional
    public void changePassword(PasswordRequest passwordRequest) {
        User user = getCurrentUser();
        if (!user.getPassword().equals(passwordEncoder.encode(passwordRequest.getOldPassword()))) {
            throw new SecurityException("Mot de passe actuel incorrect");
        }
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(registerRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user found for username " + registerRequest.getUsername()));
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(registerRequest.isEnabled());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found for username " + username));
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .created(user.getCreated())
                .build();
    }
}
