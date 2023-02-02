package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.MarqueDto;
import com.kodzotech.documentcommercial.model.Marque;

import java.util.List;

public interface MarqueService {

    /**
     * Enregistrer et modifier une marque
     *
     * @param marqueDto
     */
    void save(MarqueDto marqueDto);

    /**
     * Valider les données de la marque
     *
     * @param marque
     */
    void validerMarque(Marque marque);

    /**
     * Renvoyer la marque en fonction de son id
     *
     * @param id
     * @return
     */
    MarqueDto getMarque(Long id);

    /**
     * Renvoyer la liste des marques
     *
     * @return
     */
    List<MarqueDto> getAllMarques();

    /**
     * Supprimer une marque compte
     *
     * @param id
     */
    void delete(Long id);
}
