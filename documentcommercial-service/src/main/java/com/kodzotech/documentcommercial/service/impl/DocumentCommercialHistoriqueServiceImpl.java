package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.exception.DocumentCommercialHistoriqueException;
import com.kodzotech.documentcommercial.repository.DocumentCommercialHistoriqueRepository;
import com.kodzotech.documentcommercial.dto.DocumentCommercialHistoriqueResponse;
import com.kodzotech.documentcommercial.mapper.DocumentCommercialHistoriqueMapper;
import com.kodzotech.documentcommercial.model.DocumentCommercial;
import com.kodzotech.documentcommercial.model.DocumentCommercialHistorique;
import com.kodzotech.documentcommercial.model.EtatDocument;
import com.kodzotech.documentcommercial.service.DocumentCommercialHistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentCommercialHistoriqueServiceImpl
        implements DocumentCommercialHistoriqueService {

    private final DocumentCommercialHistoriqueRepository documentCommercialHistoriqueRepository;
    private final DocumentCommercialHistoriqueMapper documentCommercialHistoriqueMapper;

    @Override
    @Transactional
    public void save(DocumentCommercialHistorique documentCommercialHistorique) {
        documentCommercialHistoriqueRepository.save(documentCommercialHistorique);
    }

    @Override
    public void validerEntite(DocumentCommercialHistorique documentCommercialHistorique) {
        if (documentCommercialHistorique.getDocumentCommercial() == null) {
            throw new DocumentCommercialHistoriqueException(
                    "erreur.documentCommercialHistorique.documentCommercial.null");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentCommercialHistoriqueResponse> getHistorique(Long idDocument) {
        return documentCommercialHistoriqueMapper
                .entitiesToResponse(documentCommercialHistoriqueRepository
                        .findByDocumentCommercialIdOrderByDateDesc(idDocument));
    }

    @Override
    @Transactional
    public void save(DocumentCommercial documentCommercial,
                     EtatDocument etat, Long utilisateurId) {
        DocumentCommercialHistorique documentCommercialHistorique =
                DocumentCommercialHistorique
                        .builder()
                        .documentCommercial(documentCommercial)
                        .etat(etat)
                        .date(Instant.now())
                        .utilisateurId(utilisateurId)
                        .build();
        documentCommercialHistoriqueRepository.save(documentCommercialHistorique);
    }

    @Override
    @Transactional
    public void deleteByDocumentId(Long id) {
        documentCommercialHistoriqueRepository.deleteByDocumentCommercialId(id);
    }
}
