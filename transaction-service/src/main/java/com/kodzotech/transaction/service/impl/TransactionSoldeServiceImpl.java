package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.service.TransactionSoldeService;
import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.dto.DeviseValeurDto;
import com.kodzotech.transaction.dto.StatDto;
import com.kodzotech.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionSoldeServiceImpl implements TransactionSoldeService {

    private final TransactionRepository transactionRepository;
    private final DeviseService deviseService;

    @Override
    @Transactional(readOnly = true)
    public Double getSoldeCompteBancaire(Long compteBancaireId, Long deviseId) {
        return transactionRepository.soldeCompteBancaire(compteBancaireId, deviseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getSoldeCompteBancaireParDate(Long compteBancaireId, LocalDate date) {
        return transactionRepository.soldeCompteBancaire(compteBancaireId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalDepenses(Long deviseId) {
        return transactionRepository.totalDepense(deviseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalRecette(Long deviseId) {
        return transactionRepository.totalRecette(deviseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getSoldeTotal(Long deviseId) {
        return transactionRepository.soldeTotal(deviseId);
    }

    @Override
    public StatDto getStatTransaction() {
        List<DeviseDto> deviseDtos = deviseService.getAllDevise();
        List<DeviseValeurDto> depenseTotal = new ArrayList<>();
        List<DeviseValeurDto> recetteTotal = new ArrayList<>();
        List<DeviseValeurDto> soldeTotal = new ArrayList<>();
        for (DeviseDto deviseDto : deviseDtos) {
            depenseTotal.add(DeviseValeurDto.builder()
                    .devise(deviseDto.getCode())
                    .valeur(getTotalDepenses(deviseDto.getId()))
                    .build());
            recetteTotal.add(DeviseValeurDto.builder()
                    .devise(deviseDto.getCode())
                    .valeur(getTotalRecette(deviseDto.getId()))
                    .build());
            soldeTotal.add(DeviseValeurDto.builder()
                    .devise(deviseDto.getCode())
                    .valeur(getSoldeTotal(deviseDto.getId()))
                    .build());
        }
        return StatDto.builder()
                .depenses(depenseTotal)
                .recettes(recetteTotal)
                .soldes(soldeTotal)
                .build();
    }

    @Override
    public Double getTotalByDocument(Long documentId) {
        return transactionRepository.totalByDocument(documentId);
    }
}
