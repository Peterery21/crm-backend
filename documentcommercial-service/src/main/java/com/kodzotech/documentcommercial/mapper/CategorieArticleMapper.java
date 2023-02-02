package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.CategorieArticleDto;
import com.kodzotech.documentcommercial.dto.CategorieArticleResponse;
import com.kodzotech.documentcommercial.repository.CategorieArticleRepository;
import com.kodzotech.documentcommercial.model.CategorieArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CategorieArticleMapper {

    @Autowired
    public CategorieArticleRepository categorieArticleRepository;

    @Mapping(target = "parentId", source = "parent.id")
    public abstract CategorieArticleDto entityToDto(CategorieArticle categorieArticle);

    @Mapping(target = "parent", expression = "java(categorieArticleRepository.findById(categorieArticleDto.getParentId()).orElse(null))")
    public abstract CategorieArticle dtoToEntity(CategorieArticleDto categorieArticleDto);

    @Mapping(target = "id", ignore = true)
    public abstract CategorieArticle dtoToEntity(@MappingTarget CategorieArticle categorieArticleOriginal,
                                                 CategorieArticle categorieArticleModifie);

    @Mapping(target = "parent", expression = "java(entityToDto(categorieArticle.getParent()))")
    public abstract CategorieArticleResponse entityToResponse(CategorieArticle categorieArticle);

    public List<CategorieArticleResponse> entitiesToResponse(List<CategorieArticle> categorieArticles) {
        return categorieArticles.stream()
                .map(categorieArticle -> entityToResponse(categorieArticle))
                .collect(Collectors.toList());
    }
}
