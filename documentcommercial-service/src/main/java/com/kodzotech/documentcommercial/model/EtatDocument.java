package com.kodzotech.documentcommercial.model;

import java.util.Arrays;
import java.util.List;

public enum EtatDocument {
    SAISI,
    //Si validité du devis dépassé
    EXPIRE,
    //Si document confirmé/validé
    VALIDE,
    //Si document accepté ou refusé par le tier
    ACCEPTE, REFUSE,
    //Si facture générée
    FACTURE,
    //Etat de la livraison
    PARTIELLE, LIVRE,
    //Si facture en retard de paiement
    EN_RETARD,
    //Si facture réglée
    PAYE;

    public static List<EtatDocument> getEtatActions() {
        return Arrays.asList(
                SAISI, VALIDE, ACCEPTE, REFUSE
        );
    }

    public static List<EtatDocument> getEtatOk() {
        return Arrays.asList(
                VALIDE, ACCEPTE
        );
    }

    public static List<EtatDocument> getAllEtats() {
        return Arrays.asList(
                SAISI,
                EXPIRE,
                VALIDE,
                ACCEPTE, REFUSE,
                FACTURE,
                PARTIELLE, LIVRE,
                EN_RETARD,
                PAYE
        );
    }
}
