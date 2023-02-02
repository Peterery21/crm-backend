package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.dto.ModePaiementDto;
import com.kodzotech.transaction.model.ModePaiement;
import org.springframework.stereotype.Component;

@Component
public class ModePaiementMapper {

    public ModePaiement dtoToEntity(ModePaiementDto modePaiementDto) {
        return ModePaiement.builder()
                .id(modePaiementDto.getId())
                //.code(modePaiementDto.getCode())
                .libelle(modePaiementDto.getLibelle())
                .build();
    }

    public ModePaiementDto entityToDto(ModePaiement modePaiement) {
        return ModePaiementDto.builder()
                .id(modePaiement.getId())
                .code(modePaiement.getCode())
                .libelle(modePaiement.getLibelle())
                .build();
    }
}
