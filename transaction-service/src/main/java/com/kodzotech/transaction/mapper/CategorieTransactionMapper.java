package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.dto.CategorieTransactionDto;
import com.kodzotech.transaction.model.CategorieTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategorieTransactionMapper {

    public CategorieTransaction dtoToEntity(CategorieTransactionDto categorieTransactionDto);

    @Mapping(target = "id", ignore = true)
    public CategorieTransaction dtoToEntity(@MappingTarget CategorieTransaction categorieTransactionOriginal,
                                            CategorieTransaction categorieTransactionModifier);

    public CategorieTransactionDto entityToDto(CategorieTransaction categorieTransaction);

}
