package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.model.CompteBancaire;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.dto.CompteBancaireDto;
import com.kodzotech.transaction.dto.CompteBancaireResponse;
import com.kodzotech.transaction.dto.DeviseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompteBancaireMapper {


    private final DeviseService deviseService;

    public CompteBancaire dtoToEntity(CompteBancaireDto compteBancaireDto) {
        return CompteBancaire.builder()
                .id(compteBancaireDto.getId())
                .deviseId(compteBancaireDto.getDeviseId())
                .libelle(compteBancaireDto.getLibelle())
                .type(compteBancaireDto.getType())
                .build();
    }


    public CompteBancaireDto entityToDto(CompteBancaire compteBancaire) {
        return CompteBancaireDto.builder()
                .id(compteBancaire.getId())
                .deviseId(compteBancaire.getDeviseId())
                .libelle(compteBancaire.getLibelle())
                .type(compteBancaire.getType())
                .build();
    }

    public CompteBancaireResponse entityToResponse(CompteBancaire compteBancaire) {
        DeviseDto deviseDto = deviseService.getDevise(compteBancaire.getDeviseId());
        return CompteBancaireResponse.builder()
                .id(compteBancaire.getId())
                .devise(deviseDto)
                .libelle(compteBancaire.getLibelle())
                .type(compteBancaire.getType())
                .build();
    }
}
