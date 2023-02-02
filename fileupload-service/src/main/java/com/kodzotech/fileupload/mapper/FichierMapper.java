package com.kodzotech.fileupload.mapper;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.dto.FileUploadDto;
import com.kodzotech.fileupload.model.Fichier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FichierMapper {

    public Fichier dtoToEntity(FichierDto FichierDto);

    @Mapping(target = "id", ignore = true)
    public Fichier dtoToEntity(@MappingTarget Fichier FichierOriginal, Fichier FichierModifie);

    public FichierDto entityToDto(Fichier Fichier);

    public FileUploadDto fichierToFileUpload(FichierDto fichier);
}
