package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.ModePaiementDto;
import com.kodzotech.transaction.model.ModePaiement;

import java.util.List;

public interface ModePaiementService {

    /**
     * Enregistrer et modifier un mode paiement
     *
     * @param modePaiementDto
     */
    void save(ModePaiementDto modePaiementDto);

    /**
     * Valider les données du mode paiement
     *
     * @param modePaiement
     */
    void validerModePaiement(ModePaiement modePaiement);

    /**
     * Renvoyer le mode paiement en fonction de son id
     *
     * @param id
     * @return
     */
    ModePaiementDto getModePaiement(Long id);

    /**
     * Renvoyer la liste des modes paiement
     *
     * @return
     */
    List<ModePaiementDto> getAllModePaiement();

    /**
     * Supprimer un élément par son id
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
    List<ModePaiementDto> getModePaiementsById(List<Long> ids);
}
