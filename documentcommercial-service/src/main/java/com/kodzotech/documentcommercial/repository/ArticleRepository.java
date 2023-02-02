package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByCategorieArticleId(Long id);

    boolean existsByMarqueId(Long id);

    boolean existsByUniteId(Long id);

    Optional<Article> findByDesignation(String designation);

    List<Article> findAllBySocieteIdAndDisponibleAchatTrue(Long societeId);

    List<Article> findAllBySocieteIdAndDisponibleVenteTrue(Long societeId);
}