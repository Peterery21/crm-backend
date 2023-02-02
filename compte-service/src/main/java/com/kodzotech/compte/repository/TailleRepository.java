package com.kodzotech.compte.repository;

import com.kodzotech.compte.model.Taille;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TailleRepository extends JpaRepository<Taille, Long> {
    Optional<Taille> findByLibelle(String libelle);
}