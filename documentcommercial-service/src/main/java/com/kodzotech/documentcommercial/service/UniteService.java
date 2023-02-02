package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.UniteDto;
import com.kodzotech.documentcommercial.model.Unite;

import java.util.List;

public interface UniteService {

    /**
     * Enregistrer et modifier une unite
     *
     * @param uniteDto
     */
    void save(UniteDto uniteDto);

    /**
     * Valider les données de la unite
     *
     * @param unite
     */
    void validerUnite(Unite unite);

    /**
     * Renvoyer la unite en fonction de son id
     *
     * @param id
     * @return
     */
    UniteDto getUnite(Long id);

    /**
     * Renvoyer la liste des unites
     *
     * @return
     */
    List<UniteDto> getAllUnites();

    /**
     * Supprimer une unite compte
     *
     * @param id
     */
    void delete(Long id);
}
