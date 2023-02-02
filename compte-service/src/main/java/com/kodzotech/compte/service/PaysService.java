package com.kodzotech.compte.service;

import com.kodzotech.compte.dto.PaysDto;

import java.util.List;

public interface PaysService {

    /**
     * Renvoyer la liste des pays en Fr
     *
     * @return
     */
    List<PaysDto> getPaysFrList();

    /**
     * Renvoi les pays en anglais
     *
     * @return
     */
    List<PaysDto> getPaysEnList();

    /**
     * Renvoi le pays en fonction du code
     *
     * @param code
     * @return
     */
    PaysDto getPays(String code);
}
