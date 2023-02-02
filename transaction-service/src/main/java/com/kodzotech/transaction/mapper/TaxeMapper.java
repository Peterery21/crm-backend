package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.repository.TaxeRepository;
import com.kodzotech.transaction.dto.TaxeDto;
import com.kodzotech.transaction.model.Taxe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TaxeMapper {

    @Autowired
    private TaxeRepository taxeRepository;

    public abstract Taxe dtoToEntity(TaxeDto taxeDto);

    @Mapping(target = "id", ignore = true)
    public abstract Taxe dtoToEntity(@MappingTarget Taxe taxeOriginal, Taxe taxeModifie);

    @Mapping(target = "taxeFils", expression = "java(getTaxes(taxe.getIdtaxeFils()))")
    public abstract TaxeDto entityToDto(Taxe taxe);

    public List<TaxeDto> getTaxes(List<Long> idtaxeFils) {
        List<TaxeDto> taxeDtoList = new ArrayList<>();
        taxeDtoList = taxeRepository.findAllById(idtaxeFils)
                .stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toList());
        return taxeDtoList;
    }
}
