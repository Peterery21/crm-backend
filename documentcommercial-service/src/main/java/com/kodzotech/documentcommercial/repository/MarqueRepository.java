package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarqueRepository extends JpaRepository<Marque, Long> {
    Optional<Marque> findByLibelle(String libelle);
}