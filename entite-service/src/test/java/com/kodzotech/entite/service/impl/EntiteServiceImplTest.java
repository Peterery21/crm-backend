package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.dto.AdresseDto;
import com.kodzotech.entite.dto.EntiteDto;
import com.kodzotech.entite.exception.EntiteException;
import com.kodzotech.entite.model.Entite;
import com.kodzotech.entite.repository.EntiteRepository;
import com.kodzotech.entite.repository.SocieteRepository;
import com.kodzotech.entite.service.EntiteMapperService;
import com.kodzotech.entite.service.EntiteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EntiteServiceImplTest {

    @Mock
    private EntiteRepository entiteRepository;
    @Mock
    private SocieteRepository societeRepository;
    @Mock
    private EntiteMapperService entiteMapperService;
    @Mock
    private AdresseClient adresseClient;
    @InjectMocks
    private EntiteServiceImpl entiteService;

    @Test
    void save() {

    }

    @Test
    void givenEntiteInvalidNom_whenValiderEntite_thenThrowException() {
        Entite entite = new Entite();
        entite.setNom(null);
        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));
        assertEquals("erreur.entite.nom.null", exception.getMessage());
    }

    @Test
    void givenEntite_whenValiderEntite_thenVerifierDoublonNomSocieteId() {
        Entite entite = new Entite();
        entite.setNom("Nom");
        entite.setSocieteId(1L);
        Mockito.when(entiteRepository.
                findByNomAndSocieteId(entite.getNom(), entite.getSocieteId()))
                .thenReturn(Optional.of(entite));
        Mockito.when(societeRepository.existsById(entite.getSocieteId()))
                .thenReturn(true);


        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));

        assertEquals("erreur.entite.nom.doublon", exception.getMessage());
    }

    @Test
    void givenEntiteWithInvalidId_whenValiderEntite_thenThrowEntiteException() {
        Entite entite = new Entite();
        entite.setId(1L);
        entite.setNom("Nom");
        entite.setSocieteId(1L);

        Mockito.when(societeRepository.existsById(entite.getSocieteId()))
                .thenReturn(true);

        Mockito.when(entiteRepository.
                        findById(entite.getId()))
                .thenThrow(EntiteException.class);

        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));

    }

    @Test
    void givenEntiteInvalidSocieteId_whenValiderEntite_thenThrowEntiteException() {
        Entite entite = new Entite();
        entite.setId(1L);
        entite.setNom("Nom");
        entite.setSocieteId(1L);

        Mockito.when(societeRepository.existsById(entite.getSocieteId()))
                .thenReturn(false);

        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));
        assertEquals("erreur.entite.societeId.null", exception.getMessage());
    }


    @Test
    void givenEntiteWithExistingId_whenValiderEntite_thenVerifierDoublonNomSocieteId() {
        Entite entite = new Entite();
        entite.setId(1L);
        entite.setNom("Nom");
        entite.setSocieteId(1L);

        Mockito.when(societeRepository.existsById(entite.getSocieteId()))
                .thenReturn(true);

        Mockito.when(entiteRepository.
                        findById(entite.getId()))
                .thenReturn(Optional.of(entite));

        Mockito.when(entiteRepository.
                        findByNomAndSocieteId(entite.getNom(), entite.getSocieteId()))
                .thenReturn(Optional.of(Entite.builder().id(2L).build()));

        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));
        assertEquals("erreur.entite.nom.doublon", exception.getMessage());
    }

    @Test
    void givenEntiteInvalidIdSociete_whenValiderEntite_thenThrowException() {
        Entite entite = new Entite();
        entite.setNom("nom");
        entite.setSocieteId(null);
        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.validerEntite(entite));
        assertEquals("erreur.entite.societeId.null", exception.getMessage());
    }

    @Test
    void givenEntiteId_whenGetEntite_thenReturnEntiteDto() {
        Mockito.when(entiteRepository.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.ofNullable(Entite.builder()
                                .adresseId(1L).build()));
        Mockito.when(entiteMapperService.entityToDto(ArgumentMatchers.any(Entite.class)))
                .thenReturn(EntiteDto.builder().build());
        Mockito.when(adresseClient.getAdresse(1L))
                .thenReturn(AdresseDto.builder().build());

        entiteService.getEntite(ArgumentMatchers.anyLong());

        Mockito.verify(entiteRepository).findById(ArgumentMatchers.anyLong());
        Mockito.verify(entiteMapperService).entityToDto(ArgumentMatchers.any(Entite.class));
        Mockito.verify(adresseClient).getAdresse(1L);
    }

    @Test
    void givenInvalidEntiteId_whenGetEntite_thenThrowException() {
        Mockito.when(entiteRepository.findById(ArgumentMatchers.anyLong()))
                        .thenThrow(EntiteException.class);

        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.getEntite(ArgumentMatchers.anyLong()));
    }

    @Test
    void getAllEntitesBySociete() {
        List<Entite> entiteList = new ArrayList<>();
        Mockito.when(entiteRepository.findAllBySocieteId(ArgumentMatchers.anyLong()))
                .thenReturn(entiteList);

        entiteService.getAllEntitesBySociete(ArgumentMatchers.anyLong());

        Mockito.verify(entiteRepository).findAllBySocieteId(ArgumentMatchers.anyLong());
        Mockito.verify(entiteMapperService).entityToResponse(entiteList);
    }

    @Test
    void givenInvalidEntiteId_whenDelete_thenThrowException() {
        Mockito.when(entiteRepository.findById(1L))
                .thenThrow(EntiteException.class);

        EntiteException exception = assertThrows(EntiteException.class,
                () -> entiteService.delete(1L));
    }

    @Test
    void givenValidEntiteId_whenDelete_thenDeleteEntite() {
        Entite entite = Entite.builder().build();
        Mockito.when(entiteRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(entite));

        entiteService.delete(ArgumentMatchers.anyLong());

        Mockito.verify(entiteRepository).findById(ArgumentMatchers.anyLong());
        Mockito.verify(entiteRepository).delete(entite);
    }
}