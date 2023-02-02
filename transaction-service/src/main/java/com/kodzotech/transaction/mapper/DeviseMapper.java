package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.model.Devise;
import org.springframework.stereotype.Component;

@Component
public class DeviseMapper {

    public Devise dtoToEntity(DeviseDto deviseDto) {
        return Devise.builder()
                .id(deviseDto.getId())
                .code(deviseDto.getCode())
                .libelle(deviseDto.getLibelle())
                .symbole(deviseDto.getSymbole())
                .unite(deviseDto.getUnite())
                .pays(deviseDto.getPays())
                .build();
    }

    public DeviseDto entityToDto(Devise devise) {
        return DeviseDto.builder()
                .id(devise.getId())
                .code(devise.getCode())
                .libelle(devise.getLibelle())
                .symbole(devise.getSymbole())
                .unite(devise.getUnite())
                .pays(devise.getPays())
                .build();
    }
}
