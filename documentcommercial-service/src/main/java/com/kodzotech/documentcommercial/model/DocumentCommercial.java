package com.kodzotech.documentcommercial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DocumentCommercial implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CategorieDocument categorie;
    @Enumerated(EnumType.STRING)
    private TypeDocument type;
    @Enumerated(EnumType.STRING)
    private EtatDocument etat;
    private String reference;
    private String objet;
    private Long compteId;
    private Long contactId;
    private Long responsableId;
    private LocalDate dateEmission;
    private LocalDate dateExpiration;
    private LocalDate dateEcheance; // Echéance de la facture
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    List<TaxeCommande> taxes;
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    List<ArticleCommande> articles;
    private String introduction;
    private String conditionReglement;
    private String note;
    private Long adresseId;
    private Long adresseLivraisonId;
    private Long deviseId;
    private Long documentInitialId;

    private boolean actif;

    private Long societeId;
    private Long entiteId;
    private Long utilisateurId;


    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public DocumentCommercial clone()
            throws CloneNotSupportedException {
        DocumentCommercial documentCommercial = (DocumentCommercial) super.clone();
        documentCommercial.setId(null);
        documentCommercial.setArticles(new ArrayList<>());
        documentCommercial.setTaxes(new ArrayList<>());
        this.getArticles().forEach(articleCommande -> {
            try {
                ArticleCommande article = (ArticleCommande) articleCommande.clone();
                article.setId(null);
                documentCommercial.getArticles().add(article);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        this.getTaxes().forEach(taxeCommande -> {
            try {
                TaxeCommande taxe = (TaxeCommande) taxeCommande.clone();
                taxe.setId(null);
                documentCommercial.getTaxes().add(taxe);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        return documentCommercial;
    }
}
