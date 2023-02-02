package com.kodzotech.fileupload.service;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.model.Fichier;

import java.util.List;

public interface FichierService {

    /**
     * Enrégistrer le fichier dans la base
     *
     * @param FichierDto
     */
    Long save(FichierDto FichierDto);

    /**
     * Validation des données du fichier
     *
     * @param fichier
     */
    void validerFichier(Fichier fichier);

    /**
     * Supprimer des documents
     *
     * @param categorie
     * @param idObjet
     */
    void deleteByCategorieAndIdObjet(String categorie, Long idObjet);

    /**
     * Renvoyer les documents d'une catégorie et d'un objet
     *
     * @param categorie
     * @param idObjet
     * @return
     */
    List<FichierDto> getDocuments(String categorie, Long idObjet);

    /**
     * Renvoyer les documents d'une catégorie et d'un objet
     *
     * @param categorie
     * @param idObjet
     * @return
     */
    List<FichierDto> getDocumentByidObjet(String categorie, List<Long> idObjet);
}
