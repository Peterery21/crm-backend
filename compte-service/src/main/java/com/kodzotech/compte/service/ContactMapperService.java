package com.kodzotech.compte.service;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.dto.CompteDto;
import com.kodzotech.compte.dto.ContactDto;
import com.kodzotech.compte.dto.ContactResponse;
import com.kodzotech.compte.dto.ResponsableDto;
import com.kodzotech.compte.mapper.ContactMapper;
import com.kodzotech.compte.model.Contact;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMapperService {

    @Autowired
    private CompteService compteClient;
    @Autowired
    private ResponsableClient responsableClient;
    @Autowired
    private ContactMapper contactMapper;

    public Contact dtoToEntity(ContactDto contactDto){
        return contactMapper.dtoToEntity(contactDto);
    }

    public Contact dtoToEntity(Contact contactOriginal, Contact contact){
        return contactMapper.dtoToEntity(contactOriginal, contact);
    }

    public ContactDto entityToDto(Contact contact){
        return contactMapper.entityToDto(contact);
    }

    public List<ContactResponse> entityToResponse(List<Contact> contacts) {
        List<Long> compteIdList = contacts.stream().map(t -> t.getCompteId()).distinct().collect(Collectors.toList());
        List<Long> responsableIdList = contacts.stream().map(t -> t.getResponsableId()).distinct().collect(Collectors.toList());

        List<CompteDto> compteDtoList = compteClient.getComptesById(compteIdList);
        List<ResponsableDto> responsableDtoList = responsableClient.getResponsablesById(responsableIdList);

        return contacts.stream()
                .map(contact -> {
                    ContactResponse contactResponse= contactMapper.entityToResponse(contact);
                    contactResponse.setCompte(getCompte(compteDtoList, contact.getCompteId()));
                    contactResponse.setResponsable(getResponsable(responsableDtoList, contact.getResponsableId()));
                    return contactResponse;
                })
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
