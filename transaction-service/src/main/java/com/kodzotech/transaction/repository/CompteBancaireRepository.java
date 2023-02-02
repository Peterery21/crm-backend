package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.model.CompteBancaire;
import com.kodzotech.transaction.model.CompteBancaireType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {
    Optional<CompteBancaire> findByLibelle(String libelle);

    List<CompteBancaire> findAllByIdIn(List<Long> ids);

    List<CompteBancaire> findAllByType(CompteBancaireType type);
}