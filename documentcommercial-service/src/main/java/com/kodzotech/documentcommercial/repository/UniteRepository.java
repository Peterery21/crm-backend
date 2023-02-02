package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.model.Unite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniteRepository extends JpaRepository<Unite, Long> {
    Optional<Unite> findByLibelle(String libelle);
}