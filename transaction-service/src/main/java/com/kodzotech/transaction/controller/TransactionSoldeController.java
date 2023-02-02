package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.StatDto;
import com.kodzotech.transaction.service.TransactionSoldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soldes")
@RequiredArgsConstructor
public class TransactionSoldeController {

    private final TransactionSoldeService transactionSoldeService;

    @GetMapping("/bancaire/{compteBancaireId}/{deviseId}")
    @ResponseStatus(HttpStatus.OK)
    public Double getSoldeBancaire(@PathVariable Long compteBancaireId, @PathVariable Long deviseId) {
        return transactionSoldeService.getSoldeCompteBancaire(compteBancaireId, deviseId);
    }

    @GetMapping("/stat")
    @ResponseStatus(HttpStatus.OK)
    public StatDto getStatTransaction() {
        return transactionSoldeService.getStatTransaction();
    }

    @GetMapping("/document/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public Double getTotalByDocument(@PathVariable Long documentId) {
        return transactionSoldeService.getTotalByDocument(documentId);
    }
}
