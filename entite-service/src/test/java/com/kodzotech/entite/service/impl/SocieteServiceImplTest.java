package com.kodzotech.entite.service.impl;

import com.kodzotech.entite.client.AdresseClient;
import com.kodzotech.entite.repository.SocieteRepository;
import com.kodzotech.entite.service.SocieteMapperService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class SocieteServiceImplTest {
    @Mock
    private SocieteRepository societeRepository;
    @Mock
    private SocieteMapperService societeMapperService;
    @Mock
    private AdresseClient adresseClient;
    @InjectMocks
    private SocieteServiceImpl societeService;

    @Test
    void save() {
    }

    @Test
    void validerSociete() {
    }

    @Test
    void getSociete() {
    }
}