package com.kodzotech.documentcommercial.service;

import com.kodzotech.documentcommercial.dto.ArticlePlusVenduDto;
import com.kodzotech.documentcommercial.dto.EvolutionVenteParDateDto;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface DocumentCommercialRapportService {
    byte[] imprimerDocumentCommercial(String lang, Long id) throws JRException, FileNotFoundException;

    List<ArticlePlusVenduDto> getArticlePlusVenduParPeriode(Long deviseId, LocalDate dateDebut, LocalDate dateFin);

    List<EvolutionVenteParDateDto> getEvolutionVenteParDate(Long deviseId, LocalDate dateDebut, LocalDate dateFin);
}
