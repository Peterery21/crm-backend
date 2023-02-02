package com.kodzotech.documentcommercial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kodzotech.documentcommercial.model.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private ArticleType type;
    private Long categorieArticleId;
    private Long marqueId;
    private Long uniteId;
    private String reference;
    private String designation;
    private String description;
    private Double prixAchat;
    private Double prixRevient;
    private Double prixVente;
    private Boolean disponibleAchat;
    private Boolean disponibleVente;
    private List<String> images;
    private String imagePrincipale;
    private MultipartFile imagePrincipaleFile;
    private List<MultipartFile> imagesFile;
    private List<String> imagesToRemove;

    @JsonIgnore
    private Long societeId;
    @JsonIgnore
    private Long entiteId;
}
