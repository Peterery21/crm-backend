package com.kodzotech.compte.mapper;

import com.kodzotech.compte.dto.SecteurActiviteDto;
import com.kodzotech.compte.model.SecteurActivite;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SecteurActiviteMapper {

    public SecteurActivite dtoToEntity(SecteurActiviteDto secteurActiviteDto);

    public SecteurActiviteDto entityToDto(SecteurActivite secteurActivite);
}
