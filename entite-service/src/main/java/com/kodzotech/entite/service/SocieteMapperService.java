package com.kodzotech.entite.service;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.mapper.SocieteMapper;
import com.kodzotech.entite.model.Societe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocieteMapperService {

    private final AdresseClient adresseClient;
    private final SocieteMapper societeMapper;

    public Societe dtoToEntity(SocieteDto societeDto) {
        return societeMapper.dtoToEntity(societeDto);
    }

    public Societe dtoToEntity(Societe societeOriginal, Societe societe) {
        return societeMapper.dtoToEntity(societeOriginal,societe);
    }

    public SocieteDto entityToDto(Societe societe) {
        SocieteDto societeDto = societeMapper.entityToDto(societe);
        societeDto.setAdresse(adresseClient.getAdresse(societe.getId()));
        return societeDto;
    }
}
