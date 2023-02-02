package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.ContactDto;
import com.kodzotech.compte.dto.ContactResponse;
import com.kodzotech.compte.dto.StatContactDto;
import com.kodzotech.compte.model.Contact;

import java.util.List;

public interface ContactService {

    /**
     * Enregistrement des contacts
     *
     * @param contactDto
     */
    void save(ContactDto contactDto);

    /**
     * Valider les données contacts
     *
     * @param contact
     */
    void validerContact(Contact contact);

    /**
     * Récupérer un contact en fonction de l'id
     *
     * @param id
     * @return
     */
    ContactDto getContact(Long id);

    /**
     * Récupérer la liste des contacts
     *
     * @return
     */
    List<ContactResponse> getAllContact();

    /**
     * Récupérer la liste des contacts d'un compte
     *
     * @return
     */
    List<ContactResponse> getAllContactParCompte(Long idCompte);

    /**
     * Récupérer la liste des contacts par responsable
     *
     * @param idResponsable
     * @return
     */
    List<ContactResponse> getAllContactParResponsable(Long idResponsable);

    /**
     * Le total des contacts
     *
     * @return
     */
    Long getCountAllContact();

    /**
     * total par client
     *
     * @param idCompte
     * @return
     */
    Long getCountAllContactParCompte(Long idCompte);

    /**
     * Total par responsable
     *
     * @param idResponsable
     * @return
     */
    Long getCountAllContactParResponsable(Long idResponsable);

    /**
     * total non assigné
     *
     * @return
     */
    Long getCountAllContactNonAssigne();

    /**
     * Supprimer un contact
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Récupérer les contacts non assignés
     *
     * @return
     */
    List<ContactResponse> getAllContactNonAssigne();

    /**
     * Récupérer le total des contact, attribué à l'utilisateur et non assigné
     *
     * @return
     */
    StatContactDto getStatContact(Long idResponsable);

    /**
     * Récupérer la liste par page
     *
     * @param page
     * @param size
     * @return
     */
    List<ContactResponse> getAllContact(int page, int size);

    /**
     * Récupérer les contacts en fonctions de leurs ids
     *
     * @param ids
     * @return
     */
    List<ContactDto> getContactsById(List<Long> ids);
}
