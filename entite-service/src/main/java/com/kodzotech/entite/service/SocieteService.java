package com.kodzotech.entite.service;

import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Societe;

public interface SocieteService {

    /**
     * Enregistrer et modifier une societe
     *
     * @param societeDto
     */
    void save(SocieteDto societeDto);

    /**
     * Valider les données de la societe
     *
     * @param societe
     */
    void validerSociete(Societe societe);

    /**
     * Renvoyer la societe en fonction de son id
     *
     * @param id
     * @return
     */
    SocieteDto getSociete(Long id);

}
