package com.kodzotech.transaction.model;

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
public class Transaction implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double montant;
    private String description;
    @Column(nullable = false)
    private LocalDate dateTransaction;
    private int sens;
    private String reference;
    @Column(nullable = false)
    private Long compteBancaireId;
    @Column(nullable = false)
    private Long compteId;
    @Column(nullable = false)
    private Long deviseId;
    @Column(nullable = false)
    private Long modePaiementId;
    private Double taxe;
    private String numTransaction;

    @ManyToOne
    @JoinColumn(name = "categorie_transaction_id")
    private CategorieTransaction categorieTransaction;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    private Long documentId;
    //TODO Implémenter la suppression et rendre le champ supprimé à true au lieu de vraiment supprimer
    private boolean supprime;

    private Long utilisateurId;
    private Long societeId;
    private Long entiteId;

    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }
}
