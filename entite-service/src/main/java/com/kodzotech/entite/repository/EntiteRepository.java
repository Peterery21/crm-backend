package com.kodzotech.entite.repository;

import com.kodzotech.entite.model.Entite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntiteRepository extends JpaRepository<Entite, Long> {

    Optional<Entite> findByNomAndSocieteId(String nom, Long societeId);

    List<Entite> findAllBySocieteId(Long societeId);

    Boolean existsByParentId(Long parentId);

}