package com.kodzotech.documentcommercial.controller;

import com.kodzotech.documentcommercial.dto.ArticlePlusVenduDto;
import com.kodzotech.documentcommercial.dto.EvolutionVenteParDateDto;
import com.kodzotech.documentcommercial.service.DocumentCommercialRapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rapport")
@RequiredArgsConstructor
public class DocumentCommercialRapportController {

    private final DocumentCommercialRapportService documentCommercialRapportService;

    @GetMapping("/document/{id}")
    public ResponseEntity getDocumentCommercialList(@PathVariable Long id, HttpServletRequest request) {
        String lang = request.getHeader("lang");
        try {
            byte[] rapport = documentCommercialRapportService.imprimerDocumentCommercial(lang, id);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "document.pdf");
            return new ResponseEntity<byte[]>
                    (rapport, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/produitPlusVendu")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticlePlusVenduDto>
    getArticlesPlusVenduParPeriode(@RequestParam Long deviseId,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return documentCommercialRapportService.getArticlePlusVenduParPeriode(deviseId, dateDebut, dateFin);
    }

    @GetMapping("/evolutionVenteParDate")
    @ResponseStatus(HttpStatus.OK)
    public List<EvolutionVenteParDateDto>
    getEvolutionVenteParDate(@RequestParam Long deviseId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return documentCommercialRapportService.getEvolutionVenteParDate(deviseId, dateDebut, dateFin);
    }
}
