package com.kodzotech.documentcommercial.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TypeDocument {
    //VENTE
    DEVIS(10), COMMANDE(11), BON_LIVRAISON(12), FACTURE_VENTE(13),
    //ACHAT
    DEMANDE_PRIX(20), BON_COMMANDE(21), BON_RECEPTION(22), FACTURE_ACHAT(23);

    private Integer poids;

    TypeDocument(Integer poids) {
        this.poids = poids;
    }

    public static List<TypeDocument> getAchatType(TypeDocument typeEnCours) {
        return Arrays.stream(TypeDocument.values())
                .filter(typeDocument -> typeEnCours != null ?
                        typeDocument.poids > typeEnCours.poids : true)
                .filter(typeDocument -> typeEnCours != null && typeEnCours != BON_COMMANDE ?
                        typeDocument != BON_RECEPTION : true)
                .filter(typeDocument -> typeDocument.poids >= 20)
                .collect(Collectors.toList());
//        return Arrays.asList(DEMANDE_PRIX, BON_COMMANDE, BON_RECEPTION, FACTURE_ACHAT);
    }

    public static List<TypeDocument> getVenteType(TypeDocument typeEnCours) {
        return Arrays.stream(TypeDocument.values())
                .filter(typeDocument -> typeEnCours != null ?
                        typeDocument.poids > typeEnCours.poids : true)
                .filter(typeDocument -> typeEnCours != null && typeEnCours != COMMANDE ?
                        typeDocument != BON_LIVRAISON : true)
                .filter(typeDocument -> typeDocument.poids <= 15)
                .collect(Collectors.toList());
//        return Arrays.asList(DEVIS, COMMANDE, BON_LIVRAISON, FACTURE_VENTE);
    }
}
