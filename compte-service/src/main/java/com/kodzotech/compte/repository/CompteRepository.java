package com.kodzotech.compte.repository;

import com.kodzotech.compte.model.Compte;
import com.kodzotech.compte.model.CompteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    Optional<Compte> findByRaisonSociale(String raisonSociale);

    List<Compte> findAllByType(CompteType type);

    List<Compte> findAllByIdIn(List<Long> ids);

    Long countByType(CompteType type);

    boolean existsBySecteurActiviteId(Long id);

    boolean existsByCategorieCompteId(Long id);

    boolean existsByTailleId(Long id);

    boolean existsByAdresseIdOrAdresseLivraisonId(Long id1, Long id2);
}