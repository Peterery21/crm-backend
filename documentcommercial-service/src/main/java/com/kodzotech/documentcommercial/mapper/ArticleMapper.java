package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.ArticleDto;
import com.kodzotech.documentcommercial.dto.ArticleResponse;
import com.kodzotech.documentcommercial.repository.CategorieArticleRepository;
import com.kodzotech.documentcommercial.repository.MarqueRepository;
import com.kodzotech.documentcommercial.repository.UniteRepository;
import com.kodzotech.documentcommercial.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ArticleMapper {

    @Autowired
    public CategorieArticleRepository categorieArticleRepository;
    @Autowired
    public MarqueRepository marqueRepository;
    @Autowired
    public UniteRepository uniteRepository;
    @Autowired
    public CategorieArticleMapper categorieArticleMapper;
    @Autowired
    public MarqueMapper marqueMapper;
    @Autowired
    public UniteMapper uniteMapper;

    @Mapping(target = "categorieArticleId", source = "categorieArticle.id")
    @Mapping(target = "marqueId", source = "marque.id")
    @Mapping(target = "uniteId", source = "unite.id")
    public abstract ArticleDto entityToDto(Article article);

    @Mapping(target = "categorieArticle", expression = "java(articleDto.getCategorieArticleId()!=null?categorieArticleRepository.findById(articleDto.getCategorieArticleId()).orElse(null):null)")
    @Mapping(target = "marque", expression = "java(articleDto.getMarqueId()!=null?marqueRepository.findById(articleDto.getMarqueId()).orElse(null):null)")
    @Mapping(target = "unite", expression = "java(articleDto.getUniteId()!=null?uniteRepository.findById(articleDto.getUniteId()).orElse(null):null)")
    public abstract Article dtoToEntity(ArticleDto articleDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entiteId", ignore = true)
    @Mapping(target = "societeId", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    public abstract Article dtoToEntity(@MappingTarget Article articleOriginal, Article articleModifier);

    @Mapping(target = "categorieArticle", expression = "java(categorieArticleMapper.entityToDto(article.getCategorieArticle()))")
    @Mapping(target = "marque", expression = "java(marqueMapper.entityToDto(article.getMarque()))")
    @Mapping(target = "unite", expression = "java(uniteMapper.entityToDto(article.getUnite()))")
    public abstract List<ArticleResponse> entityToResponse(List<Article> articles);
}
