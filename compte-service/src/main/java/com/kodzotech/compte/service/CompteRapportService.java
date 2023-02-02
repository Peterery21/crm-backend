package com.kodzotech.compte.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface CompteRapportService {
    byte[] imprimerCompteList(String lang, Long societeId) throws JRException, FileNotFoundException;
}
