package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.*;
import com.kodzotech.documentcommercial.exception.DocumentCommercialException;
import com.kodzotech.documentcommercial.model.*;
import com.kodzotech.documentcommercial.repository.DocumentCommercialRepository;
import com.kodzotech.documentcommercial.client.CompteClient;
import com.kodzotech.documentcommercial.client.FileUploadClient;
import com.kodzotech.documentcommercial.client.ResponsableClient;
import com.kodzotech.documentcommercial.client.TransactionClient;
import com.kodzotech.documentcommercial.mapper.DocumentCommercialMapper;
import com.kodzotech.documentcommercial.service.DocumentCommercialHistoriqueService;
import com.kodzotech.documentcommercial.service.DocumentCommercialService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.kodzotech.documentcommercial.utils.TresosoftConstant.CATEGORIE_ACHAT;
import static com.kodzotech.documentcommercial.utils.TresosoftConstant.CATEGORIE_VENTE;

@Service
@RequiredArgsConstructor
public class DocumentCommercialServiceImpl implements DocumentCommercialService {

    private final DocumentCommercialRepository documentCommercialRepository;
    private final DocumentCommercialMapper documentCommercialMapper;
    private final DocumentCommercialHistoriqueService documentCommercialHistoriqueService;
    private final CompteClient compteClient;
    private final ResponsableClient responsableClient;
    private final TransactionClient transactionClient;
    private final FileUploadClient fileUploadClient;


    @Override
    @Transactional
    public Long save(DocumentCommercialDto documentCommercialDto) {
        Validate.notNull(documentCommercialDto);
        DocumentCommercial documentCommercial = documentCommercialMapper.dtoToEntity(documentCommercialDto);
        if (documentCommercial.getEtat() == null) {
            documentCommercial.setEtat(EtatDocument.SAISI);
        }
        controlerDocumentCommercial(documentCommercial);
        Long utilisateurId = null;
        //Update adresses
        AdresseDto adresseDto = documentCommercialDto.getAdresse();
        AdresseDto adresseLivraisonDto = documentCommercialDto.getAdresseLivraison();
        //Update adresse
        Long adresseId = null;
        Long adresseLivraisonId = null;
        if (adresseDto != null) {
            if (adresseDto.getId() != null) {
                adresseId = compteClient.updateAdresse(adresseDto.getId(), adresseDto);
            } else {
                adresseId = compteClient.saveAdresse(adresseDto);
            }
            documentCommercial.setAdresseId(adresseId);
        }
        if (adresseLivraisonDto != null) {
            if (adresseLivraisonDto.getId() != null) {
                adresseLivraisonId = compteClient.updateAdresse(adresseLivraisonDto.getId(),
                        adresseLivraisonDto);
            } else {
                adresseLivraisonId = compteClient.saveAdresse(adresseLivraisonDto);
            }
            documentCommercial.setAdresseLivraisonId(adresseLivraisonId);
        }
        documentCommercial.setActif(true);
        final DocumentCommercial docFinal;

        if (documentCommercial.getId() != null) {
            utilisateurId = documentCommercialDto.getUpdateUtilisateurId();
            DocumentCommercial documentCommercialOriginal = documentCommercialRepository
                    .findById(documentCommercial.getId()).get();
            documentCommercialOriginal = documentCommercialMapper.dtoToEntity(documentCommercialOriginal, documentCommercial);
            docFinal = documentCommercialRepository.save(documentCommercialOriginal);
        } else {
            utilisateurId = documentCommercialDto.getUtilisateurId();
            docFinal = documentCommercialRepository.save(documentCommercial);
        }
        //Enregistrement de l'historique
        documentCommercialHistoriqueService.save(docFinal, documentCommercial.getEtat(),
                utilisateurId);
        return docFinal.getId();
    }

    @Override
    public void controlerDocumentCommercial(DocumentCommercial documentCommercial) {

        //Vérification des champs obligatoires
        if (documentCommercial.getObjet() == null || documentCommercial.getObjet().isEmpty()) {
            throw new DocumentCommercialException("erreur.documentCommercial.objet.null");
        }

        if (documentCommercial.getEtat() == null) {
            throw new DocumentCommercialException("erreur.documentCommercial.etat.null");
        }

        if (documentCommercial.getType() == null) {
            throw new DocumentCommercialException("erreur.documentCommercial.type.incorrect");
        }

        if (documentCommercial.getDateEmission() == null || documentCommercial.getDateEmission().isAfter(LocalDate.now())) {
            throw new DocumentCommercialException("erreur.documentCommercial.date.emission.incorrect");
        }

        if (documentCommercial.getDateExpiration() != null && documentCommercial.getDateExpiration().isBefore(documentCommercial.getDateEmission())) {
            throw new DocumentCommercialException("erreur.documentCommercial.date.expiration.anterieur.date.emission");
        }

        if (documentCommercial.getDateEcheance() != null && documentCommercial.getDateEcheance().isBefore(documentCommercial.getDateEmission())) {
            throw new DocumentCommercialException("erreur.documentCommercial.date.echeance.anterieur.date.emission");
        }

        //Controler compte si renseigné
        if (documentCommercial.getCompteId() != null) {
            compteClient.getCompte(documentCommercial.getCompteId());
        }

        //controler contact si renseigné
        if (documentCommercial.getContactId() != null) {
            compteClient.getContact(documentCommercial.getContactId());
        }

        //controler Responsable si renseigné
        if (documentCommercial.getResponsableId() != null) {
            responsableClient.getResponsable(documentCommercial.getResponsableId());
        }

        if (documentCommercial.getId() != null) {
            // Mode modification
            //Rechercher le document de la base
            DocumentCommercial documentCommercialOriginal = documentCommercialRepository
                    .findById(documentCommercial.getId())
                    .orElseThrow(() ->
                            new DocumentCommercialException("erreur.documentCommercial.id.non.trouve"));
        } else {
            if (documentCommercial.getCategorie() == null) {
                throw new DocumentCommercialException("erreur.documentCommercial.categorie.null");
            }
            if (documentCommercial.getType() == null) {
                throw new DocumentCommercialException("erreur.documentCommercial.type.null");
            }
        }

        controlerListeArticle(documentCommercial.getArticles());
    }

    @Override
    public void controlerListeArticle(List<ArticleCommande> articleCommandeDtos) {
        articleCommandeDtos.stream().forEach(articleCommande -> {
            if (articleCommande.getPrixUnitaire() == null || articleCommande.getPrixUnitaire() == 0) {
                throw new DocumentCommercialException("erreur.documentCommercial.articleCommande.prixUnitaire.incorrect:"
                        + articleCommande.getReference());
            }
//            if (articleCommande.getReference() == null || articleCommande.getReference().isEmpty()) {
//                throw new DocumentCommercialException("erreur.documentCommercial.articleCommande.reference.incorrect");
//            }
            if (articleCommande.getQuantite() == null || articleCommande.getQuantite() < 1) {
                throw new DocumentCommercialException("erreur.documentCommercial.articleCommande.quantite.incorrect:"
                        + articleCommande.getReference());
            }
        });
    }

    @Override
    @Transactional
    public void modiferEtatDocument(Long id, EtatDocument etatDocument, Long utilisateurId) {
        DocumentCommercial documentCommercial = documentCommercialRepository.findById(id)
                .orElseThrow(() -> new DocumentCommercialException(
                        "erreur.documentCommercial.id.non.trouve"));
        documentCommercial.setEtat(etatDocument);
        //Vérification des données avant modification de l'état
        if (!etatDocument.equals(EtatDocument.SAISI)) {
            if (documentCommercial.getArticles().size() == 0) {
                throw new DocumentCommercialException(
                        "erreur.documentCommercial.nombre.article.incorrecte");
            }
            controlerListeArticle(documentCommercial.getArticles());
        }
        documentCommercialRepository.save(documentCommercial);
        //Enregistrement de l'historique
        documentCommercialHistoriqueService.save(documentCommercial, documentCommercial.getEtat(),
                utilisateurId);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentCommercialDto getDocumentCommercial(Long id) {
        DocumentCommercial documentCommercial = documentCommercialRepository.findById(id)
                .orElseThrow(() -> new DocumentCommercialException(
                        "erreur.documentCommercial.id.non.trouve"));
        return documentCommercialMapper.entityToDto(documentCommercial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentCommercialResponse>
    getAllDocumentCommercials(CategorieDocument categorie,
                              TypeDocument type, EtatDocument etatDocument,
                              int page, int size) {
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("dateEmission").descending());
        List<DocumentCommercial> documentCommercialList = documentCommercialRepository
                .findAllByCategorieAndTypeAndEtatAndActifTrue(categorie, type, etatDocument, pageable);
        return documentCommercialMapper.entitiesToResponse(documentCommercialList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentCommercialResponse>
    getAllDocumentCommercials(CategorieDocument categorie,
                              TypeDocument type,
                              int page, int size) {
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<DocumentCommercial> documentCommercialList = documentCommercialRepository
                .findAllByCategorieAndTypeAndActifTrue(categorie, type, pageable);
        return documentCommercialMapper.entitiesToResponse(documentCommercialList);
    }

    @Override
    public List<DocumentCommercialResponse> getAllClientDocumentCommercials(CategorieDocument categorie, TypeDocument type, Long compteId) {
        List<DocumentCommercial> documentCommercialList = documentCommercialRepository
                .findAllByCategorieAndTypeAndCompteIdAndActifTrueOrderByDateEmissionDesc(categorie, type, compteId);
        return documentCommercialMapper.entitiesToResponse(documentCommercialList);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DocumentCommercial documentCommercial = documentCommercialRepository.findById(id)
                .orElseThrow(
                        () -> new DocumentCommercialException("erreur.documentCommercial.id.non.trouve"));
        if (documentCommercial.getEtat() == EtatDocument.SAISI) {
            documentCommercialHistoriqueService.deleteByDocumentId(documentCommercial.getId());
            documentCommercialRepository.delete(documentCommercial);
        }

    }

    @Override
    @Transactional
    public Long convertirDocument(Long id, String type, Long utilisateurId) {
        Validate.notNull(id);
        Validate.notNull(type);
        DocumentCommercial documentCommercial = documentCommercialRepository.findById(id)
                .orElseThrow(() -> new DocumentCommercialException("erreur.documentCommercial.id.non.trouve"));

        controlerConversion(documentCommercial);

        DocumentCommercial newDocument = null;
        try {
            newDocument = documentCommercial.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        TypeDocument newType = Enum.valueOf(TypeDocument.class, type);

        //TODO si cest bon de livraison ou bon de reception, vérifier si tous les articles sont livrés
        //TODO si non ne pas désactivé le document précédent
        if (newType == TypeDocument.FACTURE_ACHAT || newType == TypeDocument.FACTURE_VENTE) {
            documentCommercial.setEtat(EtatDocument.FACTURE);
        }
        if (documentCommercial.getDocumentInitialId() == null) {
            documentCommercial.setDocumentInitialId(documentCommercial.getId());
        }
        documentCommercial.setActif(false);
        documentCommercialRepository.save(documentCommercial);

        newDocument.setDocumentInitialId(documentCommercial.getDocumentInitialId());
        newDocument.setType(newType);
        newDocument.setEtat(EtatDocument.SAISI);
        newDocument.setUtilisateurId(utilisateurId);

        //Vérification des données
        controlerDocumentCommercial(newDocument);

        newDocument = documentCommercialRepository.save(newDocument);

        documentCommercialHistoriqueService.save(newDocument, newDocument.getEtat(), utilisateurId);
        return newDocument.getId();
    }

    private void controlerConversion(DocumentCommercial documentCommercial) {
        if (documentCommercial.getEtat() == EtatDocument.SAISI
                || documentCommercial.getEtat() == EtatDocument.REFUSE) {
            throw new DocumentCommercialException("erreur.documentCommercial.etat.incorrect");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtatCountDto> getEtatCountList(CategorieDocument categorie, String type) {
        TypeDocument typeDocument = Enum.valueOf(TypeDocument.class, type);
        List<EtatCountDto> list = new ArrayList<>();
        list.addAll(documentCommercialRepository.getEtatCountList(categorie, typeDocument));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtatCountDto> getClientTypeCountList(CategorieDocument categorie, Long compteId) {
        List<EtatCountDto> list = new ArrayList<>();
        List<TypeDocument> typeDocumentList = getTypeDocumentAction(categorie, null);
        for (TypeDocument typeDocument : typeDocumentList) {
            List<EtatCountDto> typeCount = documentCommercialRepository.getClientTypeCountList(categorie, typeDocument, compteId);
            Long total = typeCount != null && !typeCount.isEmpty() ? typeCount.get(0).getTotal() : 0;
            list.add(EtatCountDto.builder()
                    .categorie(categorie)
                    .type(typeDocument)
                    .total(total)
                    .build());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNbreDocument(CategorieDocument categorieDocument, String type, String etat) {
        if (etat == null) {
            return documentCommercialRepository
                    .countByCategorieAndTypeAndActifTrue(
                            categorieDocument,
                            Enum.valueOf(TypeDocument.class, type)
                    );
        }
        return documentCommercialRepository
                .countByCategorieAndTypeAndEtatAndActifTrue(
                        categorieDocument,
                        Enum.valueOf(TypeDocument.class, type),
                        Enum.valueOf(EtatDocument.class, etat)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Long getClientNbreDocument(CategorieDocument categorieDocument, String type, Long compteId) {
        return documentCommercialRepository
                .countByCategorieAndTypeAndCompteIdAndActifTrue(
                        categorieDocument,
                        Enum.valueOf(TypeDocument.class, type),
                        compteId
                );
    }

    @Override
    public CategorieDocument getCategorieDocumentFromDto(String categorie) {
        CategorieDocument categorieDocument = null;
        if (categorie.equals(CATEGORIE_VENTE)) {
            categorieDocument = CategorieDocument.VENTE;
        } else if (categorie.equals(CATEGORIE_ACHAT)) {
            categorieDocument = CategorieDocument.ACHAT;
        }
        return categorieDocument;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentCommercialResponse getDocumentCommercialInfo(Long id) {
        DocumentCommercial documentCommercial = documentCommercialRepository.findById(id)
                .orElseThrow(() -> new DocumentCommercialException(
                        "erreur.documentCommercial.id.non.trouve"));
        List<DocumentCommercialResponse> docs = documentCommercialMapper.entitiesToResponse(Arrays.asList(documentCommercial));
        DocumentCommercialResponse documentCommercialResponse = !docs.isEmpty() ? docs.get(0) : null;
        Double montantRegle = transactionClient.getTotalByDocument(documentCommercialResponse.getId());
        documentCommercialResponse.setMontantRegle(montantRegle);
        documentCommercialResponse.setMontantRestant(documentCommercialResponse.getMontantHT()
                - documentCommercialResponse.getMontantRegle());
        double totalTaxe = documentCommercialResponse.getTaxes().stream().mapToDouble(e -> e.getTotal()).sum();
        documentCommercialResponse.setMontantTTC(documentCommercialResponse.getMontantHT() + totalTaxe);
        return documentCommercialResponse;
    }

    @Override
    public List<EtatDocument> getEtatsActionDocument(String categorie) {
        return EtatDocument.getEtatActions();
    }

    @Override
    public List<EtatDocument> getAllEtatsDocument(String categorie) {
        return EtatDocument.getAllEtats();
    }

    @Override
    public List<TypeDocument> getTypeDocumentAction(CategorieDocument categorieDocument, TypeDocument typeEnCours) {
        Validate.notNull(categorieDocument);
        //Validate.notNull(typeEnCours);
        if (CategorieDocument.VENTE == categorieDocument) {
            return TypeDocument.getVenteType(typeEnCours);
        } else if (CategorieDocument.ACHAT == categorieDocument) {
            return TypeDocument.getAchatType(typeEnCours);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentCommercialResponse> getDocumentLie(long documentInitialId, Long documentId) {
        return documentCommercialMapper.entitiesToResponse(documentCommercialRepository
                .findAllByDocumentInitialIdAndIdNot(documentInitialId, documentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentCommercialDto> getDocumentsById(List<Long> ids) {
        return documentCommercialRepository.findAllById(ids)
                .stream()
                .map(documentCommercialMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticlePlusVenduDto> getArticlePlusVenduParPeriode(Long deviseId, LocalDate dateDebut, LocalDate dateFin) {
        return documentCommercialRepository.getArticlePlusVenduParPeriode(deviseId, dateDebut, dateFin,
                CategorieDocument.VENTE, TypeDocument.FACTURE_VENTE, EtatDocument.getEtatOk());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolutionVenteParDateDto> getEvolutionVenteParDate(Long deviseId, LocalDate dateDebut, LocalDate dateFin) {
        return documentCommercialRepository.getEvolutionVenteParDate(deviseId, dateDebut, dateFin,
                CategorieDocument.VENTE, TypeDocument.FACTURE_VENTE, EtatDocument.getEtatOk());
    }
}
