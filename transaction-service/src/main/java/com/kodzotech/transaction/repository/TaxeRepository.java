package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.model.Taxe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxeRepository extends JpaRepository<Taxe, Long> {
    Optional<Taxe> findByLibelle(String libelle);
}