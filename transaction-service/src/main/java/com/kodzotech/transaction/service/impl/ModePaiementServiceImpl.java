package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.exception.ModePaiementException;
import com.kodzotech.transaction.mapper.ModePaiementMapper;
import com.kodzotech.transaction.model.ModePaiement;
import com.kodzotech.transaction.service.ModePaiementService;
import com.kodzotech.transaction.dto.ModePaiementDto;
import com.kodzotech.transaction.repository.ModePaiementRepository;
import com.kodzotech.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModePaiementServiceImpl implements ModePaiementService {

    private final ModePaiementRepository modePaiementRepository;
    private final ModePaiementMapper modePaiementMapper;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public void save(ModePaiementDto modePaiementDto) {
        Validate.notNull(modePaiementDto);
        ModePaiement modePaiement = modePaiementMapper.dtoToEntity(modePaiementDto);
        validerModePaiement(modePaiement);
        if (modePaiement.getId() != null) {
            ModePaiement modePaiementOriginal = modePaiementRepository
                    .findById(modePaiement.getId()).get();
            modePaiementOriginal.setLibelle(modePaiement.getLibelle());
            modePaiementOriginal.setCode(modePaiement.getCode());
            modePaiementRepository.save(modePaiementOriginal);
        } else {
            modePaiementRepository.save(modePaiement);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerModePaiement(ModePaiement modePaiement) {

        if (modePaiement.getLibelle() == null || modePaiement.getLibelle().isEmpty()) {
            throw new ModePaiementException("erreur.modePaiement.libelle.null");
        }

        if (modePaiement.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            ModePaiement modePaiementOriginal = modePaiementRepository
                    .findById(modePaiement.getId())
                    .orElseThrow(() ->
                            new ModePaiementException("erreur.modePaiement.id.non.trouve"));

            //Vérifier si libellé en double
            ModePaiement modePaiementTemp = modePaiementRepository
                    .findByLibelle(modePaiement.getLibelle())
                    .orElse(null);
            if (modePaiementTemp != null) {
                if (modePaiementTemp.getId() != modePaiementOriginal.getId()) {
                    throw new ModePaiementException("erreur.modePaiement.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            ModePaiement modePaiementTemp = modePaiementRepository
                    .findByLibelle(modePaiement.getLibelle())
                    .orElse(null);
            if (modePaiementTemp != null) {
                throw new ModePaiementException("erreur.modePaiement.libelle.doublon");
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public ModePaiementDto getModePaiement(Long id) {
        ModePaiement modePaiement = modePaiementRepository.findById(id)
                .orElseThrow(() -> new ModePaiementException(
                        "erreur.modePaiement.id.non.trouve"));
        return modePaiementMapper.entityToDto(modePaiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModePaiementDto> getAllModePaiement() {
        List<ModePaiement> modePaiementList = modePaiementRepository.findAll();
        return modePaiementList.stream()
                .map(modePaiementMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ModePaiement devise = modePaiementRepository.findById(id)
                .orElseThrow(
                        () -> new ModePaiementException("erreur.modePaiement.id.non.trouve"));
        if (transactionRepository.existsByModePaiementId(id)) {
            throw new ModePaiementException("erreur.modePaiement.utilise.transaction");
        }
        modePaiementRepository.delete(devise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModePaiementDto> getModePaiementsById(List<Long> ids) {
        return modePaiementRepository.findAllByIdIn(ids)
                .stream()
                .map(modePaiementMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
