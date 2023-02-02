package com.kodzotech.entite.service;

import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.model.Entite;

import java.util.List;

public interface EntiteService {

    /**
     * Enregistrer et modifier une entité
     *
     * @param entiteDto
     */
    void save(EntiteDto entiteDto);

    /**
     * Valider les données de l'entité
     *
     * @param entite
     */
    void validerEntite(Entite entite);

    /**
     * Renvoyer l'entité en fonction de son id
     *
     * @param id
     * @return
     */
    EntiteDto getEntite(Long id);

    /**
     * Renvoyer la liste des entités de la societe
     *
     * @return
     */
    List<EntiteResponse> getAllEntitesBySociete(Long idSociete);

    /**
     * Supprimer une entité
     *
     * @param id
     */
    void delete(Long id);
}
