package com.kodzotech.entite.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Integer niveau;
    @JoinColumn(name = "parent_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Entite parent;
    private Long societeId;
    private Long adresseId;
    private Long deviseId;
}
