package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.AdresseDto;
import com.kodzotech.compte.model.Adresse;

import java.util.List;

public interface AdresseService {

    /**
     * Enregistrer ou modifier une adresse
     *
     * @param adresseDto
     */
    Long save(AdresseDto adresseDto);

    /**
     * Valider une adresse
     *
     * @param adresse
     */
    void validerAdresse(Adresse adresse);

    /**
     * Récupérer une adresse
     *
     * @param id
     * @return
     */
    AdresseDto getAdresse(Long id);

    /**
     * Récupérer toutes les adresses
     *
     * @return
     */
    List<AdresseDto> getAllAdresses();

    /**
     * Supprimer une adresse
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Renvoyer les adresses par id
     *
     * @param ids
     * @return
     */
    List<AdresseDto> getAdressesById(List<Long> ids);
}
