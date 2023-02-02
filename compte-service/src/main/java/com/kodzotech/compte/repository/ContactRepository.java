package com.kodzotech.compte.repository;

import com.kodzotech.compte.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByCompteId(Long id);

    List<Contact> findAllByResponsableId(Long id);

    List<Contact> findAllByResponsableIdIsNull();

    Long countByCompteId(Long id);

    Long countByResponsableId(Long id);

    Long countByResponsableIdIsNull();
}
