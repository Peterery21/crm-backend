package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.exception.CategorieTransactionException;
import com.kodzotech.transaction.mapper.CategorieTransactionMapper;
import com.kodzotech.transaction.model.CategorieTransaction;
import com.kodzotech.transaction.model.SensType;
import com.kodzotech.transaction.service.CategorieTransactionService;
import com.kodzotech.transaction.service.TransactionService;
import com.kodzotech.transaction.dto.CategorieTransactionDto;
import com.kodzotech.transaction.repository.CategorieTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieTransactionServiceImpl implements CategorieTransactionService {

    private final CategorieTransactionRepository categorieTransactionRepository;
    private final CategorieTransactionMapper categorieTransactionMapper;
    private final TransactionService transactionService;

    @Override
    public void save(CategorieTransaction categorieTransaction) {
        Validate.notNull(categorieTransaction);
        verifierCategorie(categorieTransaction);
        categorieTransactionRepository.save(categorieTransaction);
    }


    @Override
    @Transactional
    public void saveCategorieDepense(CategorieTransactionDto categorieTransactionDto) {
        Validate.notNull(categorieTransactionDto);
        CategorieTransaction categorieTransaction = categorieTransactionMapper
                .dtoToEntity(categorieTransactionDto);
        categorieTransaction.setType(SensType.CATEG_DEPENSE);
        save(categorieTransaction);
    }

    @Override
    @Transactional
    public void saveCategorieRecette(CategorieTransactionDto categorieTransactionDto) {
        Validate.notNull(categorieTransactionDto);
        CategorieTransaction categorieTransaction = categorieTransactionMapper.dtoToEntity(categorieTransactionDto);
        categorieTransaction.setType(SensType.CATEG_RECETTE);
        save(categorieTransaction);
    }


    @Override
    @Transactional
    public void update(CategorieTransactionDto categorieTransactionDto) {
        Validate.notNull(categorieTransactionDto);
        CategorieTransaction categorieTransaction =
                categorieTransactionMapper
                        .dtoToEntity(categorieTransactionDto);
        verifierCategorie(categorieTransaction);
        CategorieTransaction categorieTransactionOriginal = categorieTransactionRepository
                .findById(categorieTransaction.getId()).get();
        categorieTransactionOriginal = categorieTransactionMapper.dtoToEntity(categorieTransactionOriginal,
                categorieTransaction);
        categorieTransactionRepository.save(categorieTransactionOriginal);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifierCategorie(CategorieTransaction categorieTransaction) {

        if (categorieTransaction.getLibelle() == null || categorieTransaction.getLibelle().isEmpty()) {
            throw new CategorieTransactionException("erreur.categorieTransaction.libelle.null");
        }

        if (categorieTransaction.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            CategorieTransaction categorieTransactionOriginal = categorieTransactionRepository
                    .findById(categorieTransaction.getId())
                    .orElseThrow(() ->
                            new CategorieTransactionException("erreur.categorieTransaction.id.non.trouve"));

            //Vérifier si libellé en double
            CategorieTransaction categorieTransactionTemp = categorieTransactionRepository
                    .findByLibelle(categorieTransaction.getLibelle())
                    .orElse(null);
            if (categorieTransactionTemp != null) {
                if (categorieTransactionTemp.getId() != categorieTransactionOriginal.getId()) {
                    throw new CategorieTransactionException("erreur.categorieTransaction.libelle.doublon");
                }
            }
            //Vérifier si code en double
            if (categorieTransaction.getCode() != null && !categorieTransaction.getCode().trim().isEmpty()) {
                categorieTransactionTemp = categorieTransactionRepository
                        .findByLibelle(categorieTransaction.getCode())
                        .orElse(null);
                if (categorieTransactionTemp != null) {
                    if (categorieTransactionTemp.getId() != categorieTransactionOriginal.getId()) {
                        throw new CategorieTransactionException("erreur.categorieTransaction.libelle.doublon");
                    }
                }
            }
        } else {
            // Mode ajout - vérification libellé
            CategorieTransaction categorieTransactionTemp = categorieTransactionRepository
                    .findByLibelle(categorieTransaction.getLibelle())
                    .orElse(null);
            if (categorieTransactionTemp != null) {
                throw new CategorieTransactionException("erreur.categorieTransaction.libelle.doublon");
            }
            //Vérifier si code en double
            if (categorieTransaction.getCode() != null && !categorieTransaction.getCode().trim().isEmpty()) {
                categorieTransactionTemp = categorieTransactionRepository
                        .findByCode(categorieTransaction.getCode())
                        .orElse(null);
                if (categorieTransactionTemp != null) {
                    throw new CategorieTransactionException("erreur.categorieTransaction.code.doublon");
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieTransactionDto getCategorie(Long id) {
        CategorieTransaction categorieTransaction = categorieTransactionRepository.findById(id)
                .orElseThrow(() -> new CategorieTransactionException(
                        "erreur.categorieTransaction.id.non.trouve"));
        return categorieTransactionMapper.entityToDto(categorieTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieTransactionDto> getAllCategories() {
        return categorieTransactionRepository.findAll().stream()
                .map(categorieTransactionMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieTransactionDto> getAllCategoriesByType(String type) {
        return categorieTransactionRepository.findAllByType(type).stream()
                .map(categorieTransactionMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieTransactionDto getCategorieByCode(String code) {
        CategorieTransaction categorieTransaction = categorieTransactionRepository.findByCode(code)
                .orElseThrow(() -> new CategorieTransactionException(
                        "erreur.categorieTransaction.code.non.trouve"));
        return categorieTransactionMapper.entityToDto(categorieTransaction);
    }

    @Override
    public void supprimer(Long id) {
        CategorieTransaction categorieTransaction =
                categorieTransactionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new CategorieTransactionException(
                                        "erreur.categorieTransaction.id.non.trouve"));
        if (transactionService.checkUsedCategorie(id)) {
            throw new CategorieTransactionException("erreur.categorieTransaction.utilise.compte");
        }
        categorieTransactionRepository.deleteById(id);
    }
}
