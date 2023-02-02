package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.model.DocumentCommercialHistorique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentCommercialHistoriqueRepository
        extends JpaRepository<DocumentCommercialHistorique, Long> {

    List<DocumentCommercialHistorique> findByDocumentCommercialIdOrderByDateDesc(Long idDocument);

    void deleteByDocumentCommercialId(Long id);
}