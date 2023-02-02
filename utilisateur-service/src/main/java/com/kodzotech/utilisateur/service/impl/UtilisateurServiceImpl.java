package com.kodzotech.utilisateur.service.impl;

import com.kodzotech.utilisateur.Exception.UtilisateurException;
import com.kodzotech.utilisateur.repository.UtilisateurRepository;
import com.kodzotech.utilisateur.service.UtilisateurService;
import com.kodzotech.utilisateur.dto.RegisterRequest;
import com.kodzotech.utilisateur.dto.UtilisateurDto;
import com.kodzotech.utilisateur.dto.UtilisateurResponse;
import com.kodzotech.utilisateur.mapper.SecurityMapper;
import com.kodzotech.utilisateur.mapper.UtilisateurMapper;
import com.kodzotech.utilisateur.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final SecurityMapper securityMapper;
    private final SecurityService securityService;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponse getUtilisateurByUsername(String username) {
        Utilisateur utilisateur = utilisateurRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UtilisateurException(
                        "erreur.utilisateur.username.non.trouve"));
//        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("security");
//        Supplier<UserResponse> signUpSupplier = ()->securityClient.getUser(username);
//        UserResponse userResponse = circuitBreaker.run(signUpSupplier, throwable -> {return null;});
        return utilisateurMapper.entityToResponse(utilisateur);
    }

    @Override
    @Transactional
    public void create(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateurDB = utilisateurMapper.dtoToEntity(utilisateurDto);
        validerUtilisateur(utilisateurDB);
        //Création de compte sécurité
        RegisterRequest registerRequest = securityMapper.mapDtoToRegisterRequest(utilisateurDto);

        securityService.signup(registerRequest);

        utilisateurRepository.save(utilisateurDB);

    }

    @Override
    @Transactional
    public void update(String username, UtilisateurDto utilisateurDto) {
        Utilisateur utilisateurDB = utilisateurRepository
                .findFirstByUsername(username)
                .orElseThrow(() -> new UtilisateurException("erreur.utilisateur.username.non.trouve"));
        validerUtilisateur(utilisateurDB);
        //Fill attribut
        utilisateurDB = utilisateurMapper.dtoToEntity(utilisateurDB, utilisateurDto);
        //update securite
        RegisterRequest registerRequest = securityMapper.mapDtoToRegisterRequest(utilisateurDto);
        securityService.updateUserInfo(registerRequest);
        //Save
        utilisateurRepository.save(utilisateurDB);
    }

    @Override
    @Transactional
    public void update(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateurDB = utilisateurRepository
                .findById(utilisateurDto.getId())
                .orElseThrow(() -> new UtilisateurException("erreur.utilisateur.id.non.trouve"));
        validerUtilisateur(utilisateurDB);
        //Fill attribut
        utilisateurDB = utilisateurMapper.dtoToEntity(utilisateurDB, utilisateurDto);
        //update securite
        RegisterRequest registerRequest = securityMapper.mapDtoToRegisterRequest(utilisateurDto);
        securityService.updateUserInfo(registerRequest);
        //Save
        utilisateurRepository.save(utilisateurDB);
    }

    @Override
    @Transactional(readOnly = true)
    public void validerUtilisateur(Utilisateur utilisateur) {

        if (utilisateur.getUsername() == null || utilisateur.getUsername().isEmpty()) {
            throw new UtilisateurException("erreur.utilisateur.username.null");
        }

        if (utilisateur.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Utilisateur utilisateurOriginal = utilisateurRepository
                    .findById(utilisateur.getId())
                    .orElseThrow(() ->
                            new UtilisateurException("erreur.utilisateur.id.non.trouve"));

            //Vérifier si libellé en double
            Utilisateur utilisateurTemp = utilisateurRepository
                    .findByUsername(utilisateur.getUsername())
                    .orElse(null);
            if (utilisateurTemp != null) {
                if (utilisateurTemp.getId() != utilisateurOriginal.getId()) {
                    throw new UtilisateurException("erreur.utilisateur.username.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Utilisateur utilisateurTemp = utilisateurRepository
                    .findByUsername(utilisateur.getUsername())
                    .orElse(null);
            if (utilisateurTemp != null) {
                throw new UtilisateurException("erreur.utilisateur.username.doublon");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurDto getUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurException(
                        "erreur.utilisateur.id.non.trouve"));
//        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("security");
//        Supplier<UserResponse> signUpSupplier = () -> securityClient.getUser(utilisateur.getUsername());
//        UserResponse userResponse = circuitBreaker.run(signUpSupplier, throwable -> {
//            return null;
//        });
        return utilisateurMapper.entityToDto(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurDto getUtilisateurDtoByUsername(String username) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UtilisateurException(
                        "erreur.utilisateur.id.non.trouve"));
//        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("security");
//        Supplier<UserResponse> signUpSupplier = () -> securityClient.getUser(utilisateur.getUsername());
//        UserResponse userResponse = circuitBreaker.run(signUpSupplier, throwable -> {
//            return null;
//        });
        return utilisateurMapper.entityToDto(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDto> getAllUtilisateur(Long societeId) {
        return utilisateurRepository.findBySocieteId(societeId)
                .stream()
                .map(utilisateurMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponse> getAllUtilisateurInfo(Long societeId) {
        return utilisateurRepository.findBySocieteId(societeId)
                .stream()
                .map(utilisateurMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurDto> getUtilisateursById(List<Long> ids) {
        return utilisateurRepository.findAllByIdIn(ids)
                .stream()
                .map(utilisateurMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
