package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.CompteDto;
import com.kodzotech.compte.dto.CompteResponse;
import com.kodzotech.compte.dto.StatCompteDto;
import com.kodzotech.compte.model.Compte;

import java.util.List;

public interface CompteService {

    /**
     * Enregistrer un compte
     *
     * @param compteDto
     */
    void save(CompteDto compteDto);

    /**
     * Valider les informations du compte
     *
     * @param compte
     */
    void validerCompte(Compte compte);

    /**
     * Recupérer le compte par son id
     *
     * @param id
     * @return
     */
    CompteDto getCompte(Long id);

    /**
     * Récupérer les infos externes du compte
     *
     * @param id
     * @return
     */
    CompteResponse getFullCompte(Long id);

    /**
     * Récupérer la liste des comptes avec pagination
     *
     * @param page
     * @param size
     * @return
     */
    List<CompteResponse> getAllCompte(int page, int size);

    /**
     * Récupérer la liste des comptes
     *
     * @return
     */
    List<CompteResponse> getAllCompte();

    /**
     * Récuperer la liste des clients
     *
     * @return
     */
    List<CompteResponse> getAllClient();

    /**
     * Récupérer la liste des prospects
     *
     * @return
     */
    List<CompteResponse> getAllProspect();

    /**
     * Récupérer la liste des fournisseurs
     *
     * @return
     */
    List<CompteResponse> getAllFournisseur();

    /**
     * Récupérer les comptes en fonction des ids
     *
     * @param ids
     * @return
     */
    List<CompteDto> getComptesById(List<Long> ids);

    /**
     * Nombre de compte par type
     *
     * @return
     */
    StatCompteDto getStatCompte();

    /**
     * Renvoyer le nombre total de compte
     *
     * @return
     */
    Long getNbreComptes();

    /**
     * Vérifie si le secteur est utilisé dans compte
     *
     * @return
     */
    boolean checkUsedSecteur(Long id);

    /**
     * Vérifie si la catégorie a été utilisé dans compte
     *
     * @param id
     * @return
     */
    boolean checkUsedCategorie(Long id);

    /**
     * Vérifie si la taille a été utilisé dans compte
     *
     * @param id
     * @return
     */
    boolean checkUsedTaille(Long id);

    /**
     * Vérifie si l'adresse a été utilisé dans compte
     *
     * @param id
     * @return
     */
    boolean checkUsedAdresse(Long id);

    /**
     * Supprimer un compte
     *
     * @param id
     */
    void delete(Long id);
}
