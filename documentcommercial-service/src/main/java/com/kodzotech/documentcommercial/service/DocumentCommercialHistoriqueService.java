package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.DocumentCommercialHistoriqueResponse;
import com.kodzotech.documentcommercial.model.DocumentCommercial;
import com.kodzotech.documentcommercial.model.DocumentCommercialHistorique;
import com.kodzotech.documentcommercial.model.EtatDocument;

import java.util.List;

public interface DocumentCommercialHistoriqueService {

    /**
     * enregistrer le document
     *
     * @param documentCommercialHistorique
     */
    void save(DocumentCommercialHistorique documentCommercialHistorique);

    /**
     * Valider les informations de l'historique
     *
     * @param documentCommercialHistorique
     */
    void validerEntite(DocumentCommercialHistorique documentCommercialHistorique);

    /**
     * Renvoie les différents etats du document
     *
     * @param idDocument
     * @return
     */
    List<DocumentCommercialHistoriqueResponse> getHistorique(Long idDocument);

    /**
     * Enregistrer l'historique
     *
     * @param documentCommercial
     * @param etat
     * @param utilisateurId
     */
    void save(DocumentCommercial documentCommercial, EtatDocument etat, Long utilisateurId);

    /**
     * Supprimer l'historique en fonction de l'id du document
     *
     * @param id
     */
    void deleteByDocumentId(Long id);
}
