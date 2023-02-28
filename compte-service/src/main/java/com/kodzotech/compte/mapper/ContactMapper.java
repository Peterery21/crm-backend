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
public interface ContactMapper {

    public Contact dtoToEntity(ContactDto contactDto);

    @Mapping(target = "id", ignore = true)
    public Contact dtoToEntity(@MappingTarget Contact contactOriginal, Contact contact);

    public ContactDto entityToDto(Contact contact);

    public ContactResponse entityToResponse(Contact contact);

}
