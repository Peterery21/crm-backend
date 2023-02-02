package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.*;
import com.kodzotech.documentcommercial.model.*;

import java.time.LocalDate;
import java.util.List;

public interface DocumentCommercialService {

    /**
     * Enregistrer un document
     *
     * @param documentCommercialDto
     */
    Long save(DocumentCommercialDto documentCommercialDto);

    /**
     * Controler les données
     *
     * @param documentCommercial
     */
    void controlerDocumentCommercial(DocumentCommercial documentCommercial);

    /**
     * Controler la liste des articles
     *
     * @param articleCommandeDtos
     */
    void controlerListeArticle(List<ArticleCommande> articleCommandeDtos);

    /**
     * modifier l'état du document
     *
     * @param id
     * @param etatDocument
     * @param utilisateurId
     */
    void modiferEtatDocument(Long id, EtatDocument etatDocument, Long utilisateurId);

    /**
     * Récupérer un document
     *
     * @param id
     * @return
     */
    DocumentCommercialDto getDocumentCommercial(Long id);

    /**
     * Récupérer la liste des documents
     *
     * @param categorie
     * @param type
     * @param etatDocument
     * @param page
     * @param size
     * @return
     */
    List<DocumentCommercialResponse>
    getAllDocumentCommercials(CategorieDocument categorie,
                              TypeDocument type, EtatDocument etatDocument,
                              int page, int size);

    /**
     * Récupérer la liste des documents
     *
     * @param categorie
     * @param type
     * @param page
     * @param size
     * @return
     */
    List<DocumentCommercialResponse>
    getAllDocumentCommercials(CategorieDocument categorie,
                              TypeDocument type,
                              int page, int size);

    /**
     * Renvoi la liste des documents d'un client
     *
     * @param categorie
     * @param type
     * @param compteId
     * @return
     */
    List<DocumentCommercialResponse>
    getAllClientDocumentCommercials(CategorieDocument categorie,
                                    TypeDocument type,
                                    Long compteId);


    /**
     * Supprimer un document
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Transformer un document en un autre
     *
     * @param id
     * @param type
     * @param utilisateurId
     */
    Long convertirDocument(Long id, String type, Long utilisateurId);

    /**
     * Récupérer les états et leur nombre
     * en fonction de la catégorie et du type
     *
     * @param categorie
     * @param type
     * @return
     */
    List<EtatCountDto> getEtatCountList(CategorieDocument categorie, String type);

    /**
     * Récupérer le nombre de client par type de document
     *
     * @param categorie
     * @param compteId
     * @return
     */
    List<EtatCountDto> getClientTypeCountList(CategorieDocument categorie, Long compteId);

    /**
     * Le nombre d'élement
     *
     * @param vente
     * @param type
     * @param etat
     * @return
     */
    Long getNbreDocument(CategorieDocument vente, String type, String etat);

    /**
     * Le nombre d'élement par client
     *
     * @param vente
     * @param type
     * @param compteId
     * @return
     */
    Long getClientNbreDocument(CategorieDocument vente, String type, Long compteId);

    /**
     * Récupérer la catégorie depuis le frontend
     *
     * @param categorie
     * @return
     */
    CategorieDocument getCategorieDocumentFromDto(String categorie);

    /**
     * Récupérer toutes les informations d'un document
     *
     * @param id
     * @return
     */
    DocumentCommercialResponse getDocumentCommercialInfo(Long id);

    /**
     * Renvoi les etats du document avec lesquels l'utilisateur peut interagir
     *
     * @param categorie
     * @return
     */
    List<EtatDocument> getEtatsActionDocument(String categorie);

    /**
     * Renvoi tous les états possibles
     *
     * @param categorie
     * @return
     */
    List<EtatDocument> getAllEtatsDocument(String categorie);

    /**
     * Renvoi les types pour la conversion du document, en fonction du type en cours
     *
     * @param categorieDocument
     * @param typeEnCours
     * @return
     */
    List<TypeDocument> getTypeDocumentAction(CategorieDocument categorieDocument,
                                             TypeDocument typeEnCours);

    /**
     * Récupérer tous les documents liés
     *
     * @param documentId
     * @return
     */
    List<DocumentCommercialResponse> getDocumentLie(long documentInitialId, Long documentId);

    /**
     * Récupérer les documents par id
     *
     * @param ids
     * @return
     */
    List<DocumentCommercialDto> getDocumentsById(List<Long> ids);

    /**
     * Renvoyer la liste des articles avec la somme des montants de leur vente
     * par ordre décroissant en fonction de la devise et des dates
     *
     * @param deviseId
     * @param dateDebut
     * @param dateFin
     * @return
     */
    List<ArticlePlusVenduDto> getArticlePlusVenduParPeriode(Long deviseId, LocalDate dateDebut, LocalDate dateFin);

    /**
     * Renvoyer l'évolution des ventes
     *
     * @param deviseId
     * @param dateDebut
     * @param dateFin
     * @return
     */
    List<EvolutionVenteParDateDto> getEvolutionVenteParDate(Long deviseId, LocalDate dateDebut, LocalDate dateFin);
}
