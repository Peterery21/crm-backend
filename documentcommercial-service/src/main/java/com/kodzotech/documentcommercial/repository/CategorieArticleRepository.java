package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.model.CategorieArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieArticleRepository extends JpaRepository<CategorieArticle, Long> {
    Optional<CategorieArticle> findByLibelleAndSocieteId(String libelle, Long societeId);

    List<CategorieArticle> findAllByIdIn(List<Long> categorieCompteIds);

    List<CategorieArticle> findAllBySocieteId(Long idSociete);

    Boolean existsByParentId(Long parentId);
}