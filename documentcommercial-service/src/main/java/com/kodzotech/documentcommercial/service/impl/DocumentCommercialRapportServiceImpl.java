package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.ArticlePlusVenduDto;
import com.kodzotech.documentcommercial.dto.DocumentCommercialResponse;
import com.kodzotech.documentcommercial.dto.EvolutionVenteParDateDto;
import com.kodzotech.documentcommercial.dto.SocieteDto;
import com.kodzotech.documentcommercial.client.EntiteClient;
import com.kodzotech.documentcommercial.service.DocumentCommercialRapportService;
import com.kodzotech.documentcommercial.service.DocumentCommercialService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentCommercialRapportServiceImpl implements DocumentCommercialRapportService {

    private final DocumentCommercialService documentCommercialService;
    private final EntiteClient entiteClient;

    @Override
    public byte[] imprimerDocumentCommercial(String lang, Long id) throws JRException, FileNotFoundException {
        Locale locale = Locale.forLanguageTag(lang);
        DocumentCommercialResponse documentCommercialInfo = documentCommercialService.getDocumentCommercialInfo(id);
        SocieteDto societeDto = entiteClient.getSociete(documentCommercialInfo.getSocieteId());
        Map par = new HashMap<>();
        par.put(JRParameter.REPORT_LOCALE, locale);
        par.put("societe", societeDto);
        InputStream is = getClass().getClassLoader().getResourceAsStream("templates/document_commercial_info.jrxml");
        JasperPrint compteReport =
                JasperFillManager.fillReport(JasperCompileManager.compileReport(is) // path of the jasper report
                        , par // dynamic parameters
                        , new JRBeanArrayDataSource(new DocumentCommercialResponse[]{documentCommercialInfo})
                );

        //create the report in PDF format
        return JasperExportManager.exportReportToPdf(compteReport);

    }

    @Override
    public List<ArticlePlusVenduDto> getArticlePlusVenduParPeriode(Long deviseId, LocalDate dateDebut, LocalDate dateFin) {
        return documentCommercialService.getArticlePlusVenduParPeriode(deviseId, dateDebut, dateFin);
    }

    @Override
    public List<EvolutionVenteParDateDto> getEvolutionVenteParDate(Long deviseId, LocalDate dateDebut, LocalDate dateFin) {
        return documentCommercialService.getEvolutionVenteParDate(deviseId, dateDebut, dateFin);
    }
}
