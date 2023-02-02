package com.kodzotech.compte.repository;

import com.kodzotech.compte.dto.CategorieCompteDto;
import com.kodzotech.compte.model.CategorieCompte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieCompteRepository extends JpaRepository<CategorieCompte, Long> {
    Optional<CategorieCompte> findByLibelle(String libelle);

    List<CategorieCompteDto> findAllByIdIn(List<Long> categorieCompteIds);
}