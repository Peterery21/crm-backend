package com.kodzotech.documentcommercial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DocumentCommercialHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentCommercial documentCommercial;
    @Enumerated(EnumType.STRING)
    private EtatDocument etat;
    private Instant date;
    private Long utilisateurId;
}
