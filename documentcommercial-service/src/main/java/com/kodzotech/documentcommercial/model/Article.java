package com.kodzotech.documentcommercial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ArticleType type;
    @ManyToOne
    @JoinColumn
    private CategorieArticle categorieArticle;
    @ManyToOne
    @JoinColumn
    private Marque marque;
    @ManyToOne
    @JoinColumn
    private Unite unite;
    private String reference;
    private String designation;
    @Lob
    private String description;
    private Double prixAchat;
    private Double prixRevient;
    private Double prixVente;
    private Boolean disponibleAchat;
    private Boolean disponibleVente;
    @CreationTimestamp
    private Instant dateCreation;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    private String imagePrincipale;

    private Long entiteId;
    private Long societeId;

}
