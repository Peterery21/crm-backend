package com.kodzotech.entite.service;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.dto.EntiteResponse;
import com.kodzotech.entite.dto.SocieteDto;
import com.kodzotech.entite.mapper.EntiteMapper;
import com.kodzotech.entite.model.Entite;
import com.kodzotech.entite.repository.SocieteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntiteMapperService {

    private final EntiteMapper entiteMapper;
    private final AdresseClient adresseClient;
    private final SocieteRepository societeRepository;
    private final SocieteMapperService societeMapperService;


    public List<EntiteResponse> entityToResponse(List<Entite> entites) {
        //Récupérer l'adresse
        List<Long> adresseIds = entites.stream().map(c -> c.getAdresseId()).collect(Collectors.toList());
        List<Long> societeIds = entites.stream().map(c -> c.getSocieteId()).collect(Collectors.toList());

        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? adresseClient.getAdressesById(adresseIds) : new ArrayList<>();
        List<SocieteDto> societeDtoList = societeRepository.findAllById(societeIds)
                .stream()
                .map(societeMapperService::entityToDto)
                .collect(Collectors.toList());

        return entites.stream()
                .map(entite -> {
                    EntiteResponse entiteResponse = entiteMapper.entityToResponse(entite);
                    entiteResponse.setParent(entiteMapper.entityToDto(entite.getParent()));
                    entiteResponse.setAdresse(adresseDtoList.stream()
                            .filter(object -> object.getId() != null &&
                                    object.getId().equals(entite.getAdresseId()))
                            .findFirst().orElse(null));
                    entiteResponse.setSociete(societeDtoList.stream()
                            .filter(object -> object.getId() != null &&
                                    object.getId().equals(entite.getAdresseId()))
                            .findFirst().orElse(null));
                    return entiteResponse;
                })
                .collect(Collectors.toList());
    }

    public Entite dtoToEntity(EntiteDto entiteDto) {
        return entiteMapper.dtoToEntity(entiteDto);
    }

    public Entite dtoToEntity(Entite entiteOriginal, Entite entite) {
        return entiteMapper.dtoToEntity(entiteOriginal,entite);
    }

    public EntiteDto entityToDto(Entite entite) {
        return entiteMapper.entityToDto(entite);
    }
}
