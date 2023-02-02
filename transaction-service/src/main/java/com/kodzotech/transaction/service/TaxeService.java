package com.kodzotech.transaction.service;

import com.kodzotech.transaction.dto.TaxeDto;
import com.kodzotech.transaction.model.Taxe;

import java.util.List;

public interface TaxeService {

    /**
     * Enregistrer et modifier une taxe
     *
     * @param taxeDto
     */
    void save(TaxeDto taxeDto);

    /**
     * Valider les données de la taxe
     *
     * @param taxe
     */
    void validerTaxe(Taxe taxe);

    /**
     * Renvoyer la taxe en fonction de son id
     *
     * @param id
     * @return
     */
    TaxeDto getTaxe(Long id);

    /**
     * Renvoyer la liste des taxes
     *
     * @return
     */
    List<TaxeDto> getAllTaxes();

    /**
     * Supprimer une taxe compte
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Récupérer la liste en fonction des ids
     *
     * @return
     */
    List<TaxeDto> getTaxesById(List<Long> ids);
}
