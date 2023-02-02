package com.kodzotech.compte.repository;

import com.kodzotech.compte.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {
    List<Adresse> findAllByIdIn(List<Long> ids);
}