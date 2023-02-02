package com.kodzotech.transaction.repository;

import com.kodzotech.transaction.model.Devise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviseRepository extends JpaRepository<Devise, Long> {
    Optional<Devise> findByCodeAndPays(String code, String pays);

    List<Devise> findAllByIdIn(List<Long> ids);
}
