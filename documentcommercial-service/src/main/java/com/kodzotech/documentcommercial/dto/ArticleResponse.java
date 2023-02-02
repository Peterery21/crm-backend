package com.kodzotech.documentcommercial.dto;

import com.kodzotech.documentcommercial.model.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {
    private Long id;
    private ArticleType type;
    private CategorieArticleDto categorieArticle;
    private MarqueDto marque;
    private UniteDto unite;
    private String reference;
    private String designation;
    private String description;
    private Double prixAchat;
    private Double prixRevient;
    private Double prixVente;
    private Boolean disponibleAchat;
    private Boolean disponibleVente;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Instant dateCreation;
    private Long entiteId;
    private List<String> images;
    private String imagePrincipale;
}
