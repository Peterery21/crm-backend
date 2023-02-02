package com.kodzotech.utilisateur.repository;

import com.kodzotech.utilisateur.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findFirstByUsername(String username);

    Optional<Utilisateur> findByUsername(String username);

    List<Utilisateur> findAllByIdIn(List<Long> ids);

    List<Utilisateur> findBySocieteId(Long societeId);
}
