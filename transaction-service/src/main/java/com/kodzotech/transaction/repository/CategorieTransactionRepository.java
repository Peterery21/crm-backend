package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.model.CategorieTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieTransactionRepository extends JpaRepository<CategorieTransaction, Long> {

    Optional<CategorieTransaction> findByLibelle(String libelle);

    Optional<CategorieTransaction> findByCode(String code);

    List<CategorieTransaction> findAllByType(String type);
}