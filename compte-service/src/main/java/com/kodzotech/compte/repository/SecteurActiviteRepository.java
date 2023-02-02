package com.kodzotech.compte.repository;

import com.kodzotech.compte.model.SecteurActivite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecteurActiviteRepository extends JpaRepository<SecteurActivite, Long> {
    Optional<SecteurActivite> findByLibelle(String libelle);

    List<SecteurActivite> findAllByIdIn(List<Long> ids);
}