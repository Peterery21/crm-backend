package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.SecteurActiviteDto;
import com.kodzotech.compte.model.SecteurActivite;

import java.util.List;

public interface SecteurActiviteService {

    /**
     * Enregistrer et modifier une catégorie
     *
     * @param secteurActiviteDto
     */
    void save(SecteurActiviteDto secteurActiviteDto);

    /**
     * Valider les données de la catégorie
     *
     * @param secteurActivite
     */
    void validerSecteurActivite(SecteurActivite secteurActivite);

    /**
     * Renvoyer la catégorie en fonction de son id
     *
     * @param id
     * @return
     */
    SecteurActiviteDto getSecteurActivite(Long id);

    /**
     * Renvoyer la liste des catégories
     *
     * @return
     */
    List<SecteurActiviteDto> getAllCategoriesCompte();

    /**
     * Supprimer un secteur
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Renvoyer les secteurs en fonctions des ids
     *
     * @param ids
     * @return
     */
    List<SecteurActiviteDto> getSecteurActivitesById(List<Long> ids);
}
