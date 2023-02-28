package com.kodzotech.documentcommercial.repository;

import com.kodzotech.documentcommercial.dto.ArticlePlusVenduDto;
import com.kodzotech.documentcommercial.dto.EtatCountDto;
import com.kodzotech.documentcommercial.dto.EvolutionVenteParDateDto;
import com.kodzotech.documentcommercial.model.CategorieDocument;
import com.kodzotech.documentcommercial.model.DocumentCommercial;
import com.kodzotech.documentcommercial.model.EtatDocument;
import com.kodzotech.documentcommercial.model.TypeDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DocumentCommercialRepository extends JpaRepository<DocumentCommercial, Long> {
    List<DocumentCommercial> findAllByCategorieAndTypeAndActifTrue(CategorieDocument categorie,
                                                                   TypeDocument type, Pageable pageable);

    List<DocumentCommercial> findAllByCategorieAndTypeAndCompteIdAndActifTrueOrderByDateEmissionDesc(CategorieDocument categorie,
                                                                                                     TypeDocument type, Long compteId);

    List<DocumentCommercial>
    findAllByCategorieAndTypeAndEtatAndActifTrue(CategorieDocument categorie,
                                                 TypeDocument type, EtatDocument etatDocument, Pageable pageable);


    @Query("select new com.kodzotech.documentcommercial.dto.EtatCountDto(categorie, type, etat, count(*)) " +
            "from DocumentCommercial " +
            "where categorie=:categorie and type=:type " +
            "and actif=true " +
            "group by categorie, type, etat")
    List<EtatCountDto> getEtatCountList(CategorieDocument categorie, TypeDocument type);

    @Query("select new com.kodzotech.documentcommercial.dto.EtatCountDto(categorie, type, count(*)) " +
            "from DocumentCommercial " +
            "where categorie=:categorie and type=:type and compteId=:compteId " +
            "and actif=true " +
            "group by categorie, type")
    List<EtatCountDto> getClientTypeCountList(CategorieDocument categorie, TypeDocument type, Long compteId);

    @Query("select new com.kodzotech.documentcommercial.dto.EtatCountDto(categorie, type, count(*)) " +
            "from DocumentCommercial where categorie=:categorie and type=:type " +
            "and actif=true " +
            "group by categorie, type")
    EtatCountDto getEtatCount(CategorieDocument categorie, TypeDocument type);

    Long countByCategorieAndTypeAndEtatAndActifTrue(CategorieDocument categorie, TypeDocument type, EtatDocument etat);

    Long countByCategorieAndTypeAndCompteIdAndActifTrue(CategorieDocument categorie, TypeDocument type, Long compteId);

    Long countByCategorieAndTypeAndActifTrue(CategorieDocument categorie, TypeDocument type);

    List<DocumentCommercial> findAllByDocumentInitialIdAndIdNot(Long documentInitialId, Long documentId);

    @Query("select new com.kodzotech.documentcommercial.dto.ArticlePlusVenduDto(art.articleId, art.designation, " +
            "sum((art.prixUnitaire * art.quantite)) as valeur) " +
            "from DocumentCommercial d " +
            "inner join d.articles art " +
            "WHERE d.deviseId=:deviseId " +
            "AND (d.dateEmission<=:dateFin AND d.dateEmission>=:dateDebut) " +
            "AND d.categorie=:categorie " +
            "AND d.type=:type " +
            "AND d.etat in :listEtatOk " +
            "group by art.articleId, art.designation " +
            "order by 3 DESC ")
    List<ArticlePlusVenduDto> getArticlePlusVenduParPeriode(Long deviseId, LocalDate dateDebut, LocalDate dateFin,
                                                            CategorieDocument categorie, TypeDocument type,
                                                            List<EtatDocument> listEtatOk);


    @Query("select new com.kodzotech.documentcommercial.dto.EvolutionVenteParDateDto(d.dateEmission, " +
            "sum((art.prixUnitaire * art.quantite)) as valeur) " +
            "from DocumentCommercial d " +
            "join d.articles art " +
            "WHERE d.deviseId=:deviseId " +
            "AND (d.dateEmission<=:dateFin AND d.dateEmission>=:dateDebut) " +
            "AND d.categorie=:categorie " +
            "AND d.type=:type " +
            "AND d.etat in :listEtatOk " +
            "group by d.dateEmission " +
            "order by 1 ")
    List<EvolutionVenteParDateDto> getEvolutionVenteParDate(Long deviseId, LocalDate dateDebut, LocalDate dateFin,
                                                            CategorieDocument categorie, TypeDocument type,
                                                            List<EtatDocument> listEtatOk);

}