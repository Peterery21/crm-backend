package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.exception.TaxeException;
import com.kodzotech.transaction.mapper.TaxeMapper;
import com.kodzotech.transaction.model.Taxe;
import com.kodzotech.transaction.repository.TaxeRepository;
import com.kodzotech.transaction.service.TaxeService;
import com.kodzotech.transaction.dto.TaxeDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxeServiceImpl implements TaxeService {

    private final TaxeRepository taxeRepository;
    private final TaxeMapper taxeMapper;


    @Override
    @Transactional
    public void save(TaxeDto taxeDto) {
        Validate.notNull(taxeDto);
        Taxe taxe = taxeMapper.dtoToEntity(taxeDto);
        validerTaxe(taxe);
        if (taxe.getId() != null) {
            Taxe taxeOriginal = taxeRepository
                    .findById(taxe.getId()).get();
            taxeOriginal = taxeMapper.dtoToEntity(taxeOriginal, taxe);
            taxeRepository.save(taxeOriginal);
        } else {
            taxeRepository.save(taxe);
        }
    }

    @Override
    public void validerTaxe(Taxe taxe) {

        if (taxe.getLibelle() == null || taxe.getLibelle().isEmpty()) {
            throw new TaxeException("erreur.taxe.libelle.null");
        }

        if (taxe.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Taxe taxeOriginal = taxeRepository
                    .findById(taxe.getId())
                    .orElseThrow(() ->
                            new TaxeException("erreur.taxe.id.non.trouve"));

            //Vérifier si libellé en double
            Taxe taxeTemp = taxeRepository
                    .findByLibelle(taxe.getLibelle())
                    .orElse(null);
            if (taxeTemp != null) {
                if (taxeTemp.getId() != taxeOriginal.getId()) {
                    throw new TaxeException("erreur.taxe.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Taxe taxeTemp = taxeRepository
                    .findByLibelle(taxe.getLibelle())
                    .orElse(null);
            if (taxeTemp != null) {
                throw new TaxeException("erreur.taxe.libelle.doublon");
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public TaxeDto getTaxe(Long id) {
        Taxe taxe = taxeRepository.findById(id)
                .orElseThrow(() -> new TaxeException(
                        "erreur.taxe.id.non.trouve"));
        return taxeMapper.entityToDto(taxe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxeDto> getAllTaxes() {
        List<Taxe> taxeList = taxeRepository.findAll();
        return taxeList.stream()
                .map(taxeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Taxe taxe = taxeRepository.findById(id)
                .orElseThrow(
                        () -> new TaxeException("erreur.taxe.id.non.trouve"));
        taxeRepository.delete(taxe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxeDto> getTaxesById(List<Long> ids) {
        return taxeRepository.findAllById(ids).stream()
                .map(taxeMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
