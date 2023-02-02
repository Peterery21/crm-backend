package com.kodzotech.fileupload.repository;

import com.kodzotech.fileupload.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
    List<Fichier> findByCategorieAndIdObjet(String categorie, Long idObjet);

    void deleteByCategorieAndIdObjet(String categorie, Long idObjet);

    List<Fichier> findByCategorieAndIdObjetIn(String categorie, List<Long> idObjets);
}