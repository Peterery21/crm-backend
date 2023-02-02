package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.model.ModePaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {
    Optional<ModePaiement> findByLibelle(String libelle);

    List<ModePaiement> findAllByIdIn(List<Long> ids);
}