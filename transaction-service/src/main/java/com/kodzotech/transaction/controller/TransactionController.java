package com.kodzotech.transaction.controller;

import com.kodzotech.transaction.dto.TransactionDto;
import com.kodzotech.transaction.dto.TransactionResponse;
import com.kodzotech.transaction.utils.TresosoftConstant;
import com.kodzotech.security.jwt.JwtProvider;
import com.kodzotech.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/depense")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void effectuerDepense(@RequestBody TransactionDto transactionDto,
                                 HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        transactionDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        transactionDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));
        transactionDto.setUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));

        transactionService.effectuerDepense(transactionDto);
    }

    @PostMapping("/recette")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void effectuerRecette(@RequestBody TransactionDto transactionDto,
                                 HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        transactionDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        transactionDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));
        transactionDto.setUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));

        transactionService.effectuerRecette(transactionDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void effectuerRecette(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        transactionDto.setId(id);
        transactionService.update(transactionDto);
    }

    @DeleteMapping("/annuler/{reference}")
    @ResponseStatus(code = HttpStatus.OK)
    public void annulerTransaction(@PathVariable String reference) {
        transactionService.annulerTransaction(reference);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getAllTransactions(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long entiteId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        return transactionService.getAllTransactions(entiteId);
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getLastTransactions(@PathVariable Integer page,
                                                         @PathVariable Integer size,
                                                         HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long entiteId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID);
        return transactionService.getLastTransactions(entiteId, page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Integer getNbreTransactions(HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long entiteId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID);
        return transactionService.getNbreTransactions(entiteId);
    }

    @GetMapping("/devise/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedCurrency(@PathVariable Long id) {
        return transactionService.checkUsedCurrency(id);
    }

    @GetMapping("/comptebancaire/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedCompteBancaire(@PathVariable Long id) {
        return transactionService.checkUsedCompteBancaire(id);
    }

    @GetMapping("/compte/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedCompte(@PathVariable Long id) {
        return transactionService.checkUsedCompte(id);
    }

    @GetMapping("/modepaiement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkUsedModePaiement(@PathVariable Long id) {
        return transactionService.checkUsedModePaiement(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @GetMapping("/document/{documentId}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponse> getTransactionByDocumentId(@PathVariable Long documentId) {
        return transactionService.getTransactionByDocumentId(documentId);
    }
}
