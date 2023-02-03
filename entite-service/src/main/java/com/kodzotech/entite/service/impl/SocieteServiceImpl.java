package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.client.FileUploadClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.model.Societe;
import com.kodzotech.entite.service.SocieteMapperService;
import com.kodzotech.entite.service.SocieteService;
import com.kodzotech.entite.exception.SocieteException;
import com.kodzotech.entite.repository.SocieteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocieteServiceImpl implements SocieteService {

    private final SocieteRepository societeRepository;
    private final SocieteMapperService societeMapperService;
    private final AdresseClient adresseClient;
    private final FileUploadClient fileUploadClient;

    @Override
    @Transactional
    public void save(SocieteDto societeDto) {
        Validate.notNull(societeDto);
        Societe societe = societeMapperService.dtoToEntity(societeDto);
        validerSociete(societe);

        AdresseDto adresseDto = societeDto.getAdresse();
        //Update adresse
        Long adresseId = null;
        Long adresseLivraisonId = null;
        if (adresseDto != null) {
            if (adresseDto.getId() != null) {
                adresseId = adresseClient.update(adresseDto.getId(), adresseDto);
            } else {
                adresseId = adresseClient.save(adresseDto);
            }
            societe.setAdresseId(adresseId);
        }
        if (societe.getId() != null) {
            Societe societeOriginal = societeRepository
                    .findById(societe.getId()).get();
            societeOriginal = societeMapperService.dtoToEntity(societeOriginal, societe);
            //Save societe
            societeRepository.save(societeOriginal);
        } else {
            //Save societe
            societeRepository.save(societe);
        }
    }

    @Override
    public void validerSociete(Societe societe) {

        if (societe.getRaisonSociale() == null || societe.getRaisonSociale().isEmpty()) {
            throw new SocieteException("erreur.societe.raisonSociale.null");
        }

        if (societe.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            societeRepository
                    .findById(societe.getId())
                    .orElseThrow(() ->
                            new SocieteException("erreur.societe.id.non.trouve"));
        }
    }


    @Override
    public SocieteDto getSociete(Long id) {
        Societe societe = societeRepository.findById(id)
                .orElseThrow(() -> new SocieteException(
                        "erreur.societe.id.non.trouve"));
        return societeMapperService.entityToDto(societe);
    }

}
