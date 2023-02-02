package com.kodzotech.compte.mapper;

import com.kodzotech.compte.dto.SecteurActiviteDto;
import com.kodzotech.compte.model.SecteurActivite;
import org.springframework.stereotype.Component;

@Component
public class SecteurActiviteMapper {

    public SecteurActivite dtoToEntity(SecteurActiviteDto secteurActiviteDto) {
        return SecteurActivite.builder()
                .id(secteurActiviteDto.getId())
                .libelle(secteurActiviteDto.getLibelle())
                .build();
    }

    public SecteurActiviteDto entityToDto(SecteurActivite secteurActivite) {
        return SecteurActiviteDto.builder()
                .id(secteurActivite.getId())
                .libelle(secteurActivite.getLibelle())
                .build();
    }
}
