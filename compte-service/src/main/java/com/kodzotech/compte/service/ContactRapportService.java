package com.kodzotech.compte.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ContactRapportService {
    byte[] imprimerList(String lang, Long societeId) throws JRException, FileNotFoundException;
}
