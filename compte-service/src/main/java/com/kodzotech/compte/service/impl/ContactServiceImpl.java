package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.dto.*;
import com.kodzotech.compte.exception.ContactException;
import com.kodzotech.compte.model.Contact;
import com.kodzotech.compte.repository.ContactRepository;
import com.kodzotech.compte.service.CompteService;
import com.kodzotech.compte.service.ContactMapperService;
import com.kodzotech.compte.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapperService contactMapperService;
    private final CompteService compteService;
    private final ResponsableClient responsableClient;

    @Override
    @Transactional
    public void save(ContactDto contactDto) {
        Validate.notNull(contactDto);
        Contact contact = contactMapperService.dtoToEntity(contactDto);
        validerContact(contact);
        if (contact.getId() != null) {
            Contact contactOriginal = contactRepository
                    .findById(contact.getId()).get();
            contactOriginal = contactMapperService.dtoToEntity(contactOriginal, contact);
            contactRepository.save(contactOriginal);
        } else {
            contactRepository.save(contact);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerContact(Contact contact) {

        if (contact.getNom() == null || contact.getNom().isEmpty()) {
            throw new ContactException("erreur.contact.nom.null");
        }

        if (contact.getCompteId() == null) {
            throw new ContactException("erreur.contact.compte.null");
        }

        //Vérifier existance du compte
        CompteDto compteDto = compteService.getCompte(contact.getCompteId());
        if (compteDto == null) {
            throw new ContactException("erreur.contact.compte.non.trouve");
        }

        if (contact.getResponsableId() != null) {
            //Vérification de l'existance de l'utilisateur
            ResponsableDto responsableDto = responsableClient
                    .getResponsable(contact.getResponsableId());
            if (responsableDto == null) {
                throw new ContactException("erreur.contact.responsable.non.valide");
            }
        }

        if (contact.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Contact contactOriginal = contactRepository
                    .findById(contact.getId())
                    .orElseThrow(() ->
                            new ContactException("erreur.contact.id.non.trouve"));

            //Vérification de doublon à revoir
        } else {
            //Vérification de doublon à revoir
        }
    }


    @Override
    @Transactional(readOnly = true)
    public ContactDto getContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactException(
                        "erreur.contact.id.non.trouve"));
        return contactMapperService.entityToDto(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContact() {
        List<Contact> contactList = contactRepository.findAll();
        return contactMapperService.entityToResponse(contactList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContactParCompte(Long idCompte) {
        List<Contact> contactList = contactRepository.findAllByCompteId(idCompte);
        return contactMapperService.entityToResponse(contactList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContactParResponsable(Long idResponsable) {
        List<Contact> contactList = contactRepository.findAllByResponsableId(idResponsable);
        return contactMapperService.entityToResponse(contactList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContactNonAssigne() {
        List<Contact> contactList = contactRepository.findAllByResponsableIdIsNull();
        return contactMapperService.entityToResponse(contactList);
    }

    @Override
    @Transactional(readOnly = true)
    public StatContactDto getStatContact(Long idResponsable) {
        return StatContactDto
                .builder()
                .totalContact(getCountAllContact())
                .totalMesContacts(getCountAllContactParResponsable(idResponsable))
                .totalNonAssigne(getCountAllContactNonAssigne())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContact(int page, int size) {
        Pageable sortedByDateDesc =
                PageRequest.of(page, size, Sort.by("nom").ascending());
        List<Contact> contactList = contactRepository.findAll(sortedByDateDesc).toList();
        return contactMapperService.entityToResponse(contactList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> getContactsById(List<Long> ids) {
        return contactRepository.findAllById(ids)
                .stream()
                .map(contactMapperService::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountAllContact() {
        return contactRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountAllContactParCompte(Long idCompte) {
        return contactRepository.countByCompteId(idCompte);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountAllContactParResponsable(Long idResponsable) {
        return contactRepository.countByResponsableId(idResponsable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountAllContactNonAssigne() {
        return contactRepository.countByResponsableIdIsNull();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(
                        () -> new ContactException("erreur.contact.id.non.trouve"));
        contactRepository.delete(contact);
    }
}
