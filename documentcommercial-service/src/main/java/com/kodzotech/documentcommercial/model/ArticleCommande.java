package com.kodzotech.documentcommercial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ArticleCommande implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long articleId;
    private String reference;
    private String designation;
    @Lob
    private String description;
    private String unite;
    private Double prixRevient;
    private Double prixUnitaire;
    private Integer quantite;
    private Long taxeId;

    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }
}
