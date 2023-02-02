package com.kodzotech.compte.mapper;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.dto.CompteDto;
import com.kodzotech.compte.dto.ContactDto;
import com.kodzotech.compte.dto.ContactResponse;
import com.kodzotech.compte.dto.ResponsableDto;
import com.kodzotech.compte.model.Contact;
import com.kodzotech.compte.service.CompteService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    @Autowired
    private CompteService compteClient;
    @Autowired
    private ResponsableClient responsableClient;

    public abstract Contact dtoToEntity(ContactDto contactDto);

    @Mapping(target = "id", ignore = true)
    public abstract Contact dtoToEntity(@MappingTarget Contact contactOriginal, Contact contact);

    public abstract ContactDto entityToDto(Contact contact);

    @Mapping(target = "compte", expression = "java(getCompte(compteDtoList, contact.getCompteId()))")
    @Mapping(target = "responsable", expression = "java(getResponsable(responsableDtoList, contact.getResponsableId()))")
    public abstract ContactResponse entityToResponse(Contact contact,
                                                     List<CompteDto> compteDtoList,
                                                     List<ResponsableDto> responsableDtoList);

    public List<ContactResponse> entityToResponse(List<Contact> contacts) {
        List<Long> compteIdList = contacts.stream().map(t -> t.getCompteId()).distinct().collect(Collectors.toList());
        List<Long> responsableIdList = contacts.stream().map(t -> t.getResponsableId()).distinct().collect(Collectors.toList());

        List<CompteDto> compteDtoList = compteClient.getComptesById(compteIdList);
        List<ResponsableDto> responsableDtoList = responsableClient.getResponsablesById(responsableIdList);

        return contacts.stream()
                .map(contact -> entityToResponse(contact, compteDtoList, responsableDtoList))
                .collect(Collectors.toList());
    }

    CompteDto getCompte(List<CompteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new CompteDto());
    }

    ResponsableDto getResponsable(List<ResponsableDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(new ResponsableDto());
    }
}
