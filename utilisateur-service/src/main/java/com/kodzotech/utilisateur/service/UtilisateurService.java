package com.kodzotech.utilisateur.service;

import com.kodzotech.utilisateur.dto.UtilisateurDto;
import com.kodzotech.utilisateur.dto.UtilisateurResponse;
import com.kodzotech.utilisateur.model.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    /**
     * Renvoi l'utilisateur en fonction de l'username
     *
     * @param username
     * @return
     */
    UtilisateurResponse getUtilisateurByUsername(String username);

    /**
     * enregistrer un utilisateur
     *
     * @param utilisateurDto
     */
    void create(UtilisateurDto utilisateurDto);

    /**
     * Modifier un utilisateur
     *
     * @param username
     * @param utilisateurDto
     */
    void update(String username, UtilisateurDto utilisateurDto);

    void update(UtilisateurDto utilisateurDto);

    /**
     * Valider les informations d'un utilisateur
     *
     * @param utilisateur
     */
    void validerUtilisateur(Utilisateur utilisateur);

    /**
     * Récupérer un utilisateur
     *
     * @param id
     * @return
     */
    UtilisateurDto getUtilisateur(Long id);

    /**
     * Récupérer les infos basiques de l'utilisateur via son username
     *
     * @param username
     * @return
     */
    UtilisateurDto getUtilisateurDtoByUsername(String username);

    /**
     * Récupérer la liste des utilisateurs
     *
     * @return
     */
    List<UtilisateurDto> getAllUtilisateur(Long societeId);

    /**
     * Récupérer les utilisateurs avec leur entité
     *
     * @param societeId
     * @return
     */
    List<UtilisateurResponse> getAllUtilisateurInfo(Long societeId);

    /**
     * Récupérer les utilisateurs par leur Ids
     *
     * @param ids
     * @return
     */
    List<UtilisateurDto> getUtilisateursById(List<Long> ids);
}
