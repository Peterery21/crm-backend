package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.client.EntiteClient;
import com.kodzotech.compte.dto.CompteResponse;
import com.kodzotech.compte.dto.SocieteDto;
import com.kodzotech.compte.service.CompteRapportService;
import com.kodzotech.compte.service.CompteService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompteRapportServiceImpl implements CompteRapportService {

    private final CompteService compteService;
    private final EntiteClient entiteClient;

    @Override
    public byte[] imprimerCompteList(String lang, Long societeId) throws JRException, FileNotFoundException {

        Locale locale = Locale.forLanguageTag(lang);
        Map par = new HashMap<>();
        par.put(JRParameter.REPORT_LOCALE, locale);
        SocieteDto societeDto = entiteClient.getSociete(societeId);
        par.put("societe", societeDto);
        List<CompteResponse> compteResponseList = compteService.getAllCompte();
        InputStream is = getClass().getClassLoader().getResourceAsStream("templates/compte_list.jrxml");
        JasperPrint compteReport =
                JasperFillManager.fillReport(JasperCompileManager.compileReport(is) // path of the jasper report
                        , par // dynamic parameters
                        , new JRBeanCollectionDataSource(compteResponseList)
                );

        //create the report in PDF format
        return JasperExportManager.exportReportToPdf(compteReport);
    }
}
