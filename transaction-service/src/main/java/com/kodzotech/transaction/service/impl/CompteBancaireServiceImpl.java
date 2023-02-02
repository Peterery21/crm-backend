package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.exception.CompteBancaireException;
import com.kodzotech.transaction.model.CompteBancaire;
import com.kodzotech.transaction.repository.CompteBancaireRepository;
import com.kodzotech.transaction.service.CompteBancaireService;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.dto.CompteBancaireDto;
import com.kodzotech.transaction.dto.CompteBancaireResponse;
import com.kodzotech.transaction.mapper.CompteBancaireMapper;
import com.kodzotech.transaction.model.CompteBancaireType;
import com.kodzotech.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CompteBancaireServiceImpl implements CompteBancaireService {

    private final CompteBancaireRepository compteBancaireRepository;
    private final DeviseService deviseService;
    private final CompteBancaireMapper compteBancaireMapper;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void save(CompteBancaireDto compteBancaireDto) {
        Validate.notNull(compteBancaireDto);
        CompteBancaire compteBancaire = compteBancaireMapper.dtoToEntity(compteBancaireDto);
        validerCompteBancaire(compteBancaire);
        if (compteBancaire.getId() != null) {
            CompteBancaire compteBancaireOriginal = compteBancaireRepository
                    .findById(compteBancaire.getId()).get();
            compteBancaireOriginal.setLibelle(compteBancaire.getLibelle());
            compteBancaireOriginal.setType(compteBancaire.getType());
            compteBancaireOriginal.setDeviseId(compteBancaire.getDeviseId());
            compteBancaireRepository.save(compteBancaireOriginal);
        } else {
            compteBancaireRepository.save(compteBancaire);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerCompteBancaire(CompteBancaire compteBancaire) {

        if (compteBancaire.getLibelle() == null || compteBancaire.getLibelle().isEmpty()) {
            throw new CompteBancaireException("erreur.compteBancaire.libelle.null");
        }

        if (compteBancaire.getType() == null) {
            throw new CompteBancaireException("erreur.compteBancaire.type.incorrect");
        }

        if (compteBancaire.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            CompteBancaire compteBancaireOriginal = compteBancaireRepository
                    .findById(compteBancaire.getId())
                    .orElseThrow(() ->
                            new CompteBancaireException("erreur.compteBancaire.id.non.trouve"));

            //Vérifier si libellé en double
            CompteBancaire compteBancaireTemp = compteBancaireRepository
                    .findByLibelle(compteBancaire.getLibelle())
                    .orElse(null);
            if (compteBancaireTemp != null) {
                if (compteBancaireTemp.getId() != compteBancaireOriginal.getId()) {
                    throw new CompteBancaireException("erreur.compteBancaire.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            CompteBancaire compteBancaireTemp = compteBancaireRepository
                    .findByLibelle(compteBancaire.getLibelle())
                    .orElse(null);
            if (compteBancaireTemp != null) {
                throw new CompteBancaireException("erreur.compteBancaire.libelle.doublon");
            }
        }
        //Vérifier Devise
        if (compteBancaire.getDeviseId() == null) {
            throw new CompteBancaireException("erreur.transaction.devise.non.valide");
        } else {
            deviseService.getDevise(compteBancaire.getDeviseId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompteBancaireDto getCompteBancaire(Long id) {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(id)
                .orElseThrow(() -> new CompteBancaireException(
                        "erreur.compteBancaire.id.non.trouve"));
        return compteBancaireMapper.entityToDto(compteBancaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteBancaireResponse> getAllCompteBancaire() {
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        return compteBancaireList.stream()
                .flatMap(compteBancaire -> Stream.of(compteBancaireMapper.entityToResponse(compteBancaire)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        CompteBancaire compteBancaire = compteBancaireRepository
                .findById(id)
                .orElseThrow(() ->
                        new CompteBancaireException("erreur.compteBancaire.id.non.trouve"));
        if (transactionRepository.existsByCompteBancaireId(id)) {
            throw new CompteBancaireException("erreur.compteBancaire.utilise.transaction");
        }
        compteBancaireRepository.delete(compteBancaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteBancaireDto> getCompteBancairesById(List<Long> ids) {
        return compteBancaireRepository.findAllByIdIn(ids)
                .stream()
                .map(compteBancaireMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteBancaireDto> getCompteBancaireParType(CompteBancaireType type) {
        return compteBancaireRepository.findAllByType(type)
                .stream()
                .map(compteBancaireMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
