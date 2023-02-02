package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.TailleDto;
import com.kodzotech.compte.model.Taille;

import java.util.List;

public interface TailleService {

    /**
     * Enregistrer et modifier une taille
     *
     * @param tailleDto
     */
    void save(TailleDto tailleDto);

    /**
     * Valider les données de la taille
     *
     * @param taille
     */
    void validerTaille(Taille taille);

    /**
     * Renvoyer la taille en fonction de son id
     *
     * @param id
     * @return
     */
    TailleDto getTaille(Long id);

    /**
     * Renvoyer la liste des tailles
     *
     * @return
     */
    List<TailleDto> getAllTailles();

    /**
     * Supprimer une taille compte
     *
     * @param id
     */
    void delete(Long id);
}
