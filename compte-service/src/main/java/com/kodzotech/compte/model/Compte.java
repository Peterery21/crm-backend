package com.kodzotech.compte.model;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CompteType type;
    private Boolean estSociete;
    private String raisonSociale;
    @ManyToOne
    @JoinColumn(name = "categorie_compte_id")
    private CategorieCompte categorieCompte;
    @ManyToOne
    @JoinColumn(name = "taille_id")
    private Taille taille;
    private Long secteurActiviteId;
    private Long responsableId;
    private Long adresseId;
    private Long adresseLivraisonId;
    private Boolean adresseIdentique;
    private String email;
    private String site;
    private String registreCommerce;
    
    private LocalDate dateCreation;

    private Long utilisateurId;
    private Long societeId;
    private Long entiteId;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
