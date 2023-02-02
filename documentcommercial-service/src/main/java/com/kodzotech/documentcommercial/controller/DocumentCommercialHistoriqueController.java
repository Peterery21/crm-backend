package com.kodzotech.documentcommercial.controller;

import com.kodzotech.documentcommercial.dto.DocumentCommercialHistoriqueResponse;
import com.kodzotech.documentcommercial.service.DocumentCommercialHistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents/historique")
@RequiredArgsConstructor
public class DocumentCommercialHistoriqueController {
    private final DocumentCommercialHistoriqueService documentCommercialHistoriqueService;

    @GetMapping("/{idDocument}")
    public List<DocumentCommercialHistoriqueResponse> getHistorique(@PathVariable Long idDocument) {
        return documentCommercialHistoriqueService.getHistorique(idDocument);
    }
}
