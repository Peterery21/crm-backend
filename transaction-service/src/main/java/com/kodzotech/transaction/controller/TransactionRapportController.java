package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.rapport.ResultatExploitationDto;
import com.kodzotech.transaction.dto.rapport.TransactionCategorieDto;
import com.kodzotech.transaction.dto.rapport.TransactionCompteBancaireDto;
import com.kodzotech.transaction.dto.rapport.TransactionJourDto;
import com.kodzotech.transaction.service.TransactionRapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rapports")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionRapportController {

    private final TransactionRapportService transactionRapportService;

    @GetMapping("/transactionParPeriode")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionCategorieDto>
    getTransactionCategorieParPeriode(@RequestParam Long deviseId,
                                      @RequestParam String sensType,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return transactionRapportService.getTransactionCategorieParPeriode(deviseId, sensType, dateDebut, dateFin);
    }

    @GetMapping("/transactionParJour")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionJourDto>
    getTransactionCategorieParJour(@RequestParam Long deviseId,
                                   @RequestParam String sensType,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return transactionRapportService.getTransactionCategorieParJour(deviseId, sensType, dateDebut, dateFin);
    }

    @GetMapping("/transactionParJour/print")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity
    printTransactionCategorieParJour(@RequestParam Long deviseId,
                                     @RequestParam String sensType,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
                                     HttpServletRequest request) {
        try {
            byte[] rapport = transactionRapportService.printTransactionCategorieParJour(deviseId, sensType, dateDebut, dateFin, request);
            ;
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "document.pdf");
            return new ResponseEntity<byte[]>
                    (rapport, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactionParCompteBancaire")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionCompteBancaireDto>
    getTransactionParBanque(@RequestParam List<Long> compteBancaireIds,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return transactionRapportService.getTransactionParBanque(compteBancaireIds, dateDebut, dateFin);
    }

    @GetMapping("/resultat")
    @ResponseStatus(HttpStatus.OK)
    public ResultatExploitationDto
    getResultatExploitation(@RequestParam Long deviseId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return transactionRapportService.getResultatExploitation(deviseId, dateDebut, dateFin);
    }
}
