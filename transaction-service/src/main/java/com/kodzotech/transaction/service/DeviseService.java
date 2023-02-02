package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.model.Devise;

import java.util.List;

public interface DeviseService {

    /**
     * Enregistrer une devise
     *
     * @param deviseDto
     */
    void save(DeviseDto deviseDto);

    /**
     * Gestion des erreurs
     *
     * @param devise
     */
    void validerDevise(Devise devise);

    /**
     * Renvoyer la devise en fonction de l'id
     *
     * @param id
     * @return
     */
    DeviseDto getDevise(Long id);

    /**
     * Liste récuperer depuis currency.json
     *
     * @return
     */
    List<DeviseDto> getDefaultList();

    /**
     * Renvoi la liste des devises depuis la base
     *
     * @return
     */
    List<DeviseDto> getAllDevise();

    /**
     * Supprimer la devise
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Récupérer la liste en fonction des ids
     *
     * @param ids
     * @return
     */
    List<DeviseDto> getDevisesById(List<Long> ids);
}
