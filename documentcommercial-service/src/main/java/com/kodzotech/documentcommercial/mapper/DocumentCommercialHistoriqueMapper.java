package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.ResponsableDto;
import com.kodzotech.documentcommercial.client.ResponsableClient;
import com.kodzotech.documentcommercial.dto.DocumentCommercialHistoriqueResponse;
import com.kodzotech.documentcommercial.model.DocumentCommercialHistorique;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class DocumentCommercialHistoriqueMapper {

    @Autowired
    private ResponsableClient responsableClient;

    public abstract DocumentCommercialHistoriqueResponse entityToResponse(
            DocumentCommercialHistorique documentCommercialHistorique);

    @Mapping(target = "utilisateur", expression = "java(getResponsable(responsables, documentCommercialHistorique.getUtilisateurId()))")
    public abstract DocumentCommercialHistoriqueResponse entityToResponse(
            DocumentCommercialHistorique documentCommercialHistorique,
            List<ResponsableDto> responsables);

    public List<DocumentCommercialHistoriqueResponse> entitiesToResponse(
            List<DocumentCommercialHistorique> documentCommercialHistoriques) {
        List<Long> responsableIdList = documentCommercialHistoriques.stream()
                .filter(t -> t.getUtilisateurId() != null)
                .map(t -> t.getUtilisateurId())
                .distinct()
                .collect(Collectors.toList());
        List<ResponsableDto> responsableDtoList = responsableIdList.size() > 0 ?
                responsableClient.getResponsablesById(responsableIdList) : new ArrayList<>();
        return documentCommercialHistoriques.stream()
                .map(documentCommercialHistorique ->
                        entityToResponse(documentCommercialHistorique, responsableDtoList))
                .collect(Collectors.toList());
    }

    ResponsableDto getResponsable(List<ResponsableDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }
}
