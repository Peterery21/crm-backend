package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.client.CompteClient;
import com.kodzotech.documentcommercial.dto.*;
import com.kodzotech.documentcommercial.client.ResponsableClient;
import com.kodzotech.documentcommercial.client.TransactionClient;
import com.kodzotech.documentcommercial.model.ArticleCommande;
import com.kodzotech.documentcommercial.model.DocumentCommercial;
import com.kodzotech.documentcommercial.model.EtatDocument;
import com.kodzotech.documentcommercial.model.TypeDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ArticleCommandeMapper.class})
public abstract class DocumentCommercialMapper {

    @Autowired
    private CompteClient compteClient;
    @Autowired
    private ResponsableClient responsableClient;
    @Autowired
    private TransactionClient transactionClient;

    public static DocumentCommercialMapper INSTANCE = Mappers.getMapper(DocumentCommercialMapper.class);

    public abstract DocumentCommercial dtoToEntity(DocumentCommercialDto documentCommercialDto);

    @Mapping(target = "adresse", expression = "java(getAdresse(adresses, documentCommercial.getAdresseId()))")
    @Mapping(target = "adresseLivraison", expression = "java(getAdresse(adresses, documentCommercial.getAdresseLivraisonId()))")
    abstract DocumentCommercialDto entityToDto(DocumentCommercial documentCommercial,
                                               List<AdresseDto> adresses);

    public DocumentCommercialDto entityToDto(DocumentCommercial documentCommercial) {
        List<Long> adresseIds = new ArrayList<>();
        adresseIds.add(documentCommercial.getAdresseId());
        adresseIds.add(documentCommercial.getAdresseLivraisonId());
        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? compteClient.getAdressesById(adresseIds) : new ArrayList<>();
        return entityToDto(documentCommercial, adresseDtoList);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "documentInitialId", ignore = true)
    @Mapping(target = "entiteId", ignore = true)
    @Mapping(target = "societeId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract DocumentCommercial dtoToEntity(@MappingTarget DocumentCommercial documentCommercialOriginal,
                                                   DocumentCommercial documentCommercialModifie);


    @Mapping(target = "compte", expression = "java(getCompte(comptes, documentCommercial.getCompteId()))")
    @Mapping(target = "responsable", expression = "java(getResponsable(responsables, documentCommercial.getResponsableId()))")
    @Mapping(target = "contact", expression = "java(getContact(contacts, documentCommercial.getContactId()))")
    @Mapping(target = "adresse", expression = "java(getAdresse(adresses, documentCommercial.getAdresseId()))")
    @Mapping(target = "adresseLivraison", expression = "java(getAdresse(adresses, documentCommercial.getAdresseLivraisonId()))")
    @Mapping(target = "devise", expression = "java(getDevise(devises, documentCommercial.getDeviseId()))")
    @Mapping(target = "montantHT", expression = "java(getMontantHT(documentCommercial.getArticles()))")
    @Mapping(target = "etat", expression = "java(getEtat(documentCommercial))")
    abstract DocumentCommercialResponse entityToResponse(DocumentCommercial documentCommercial,
                                                         List<CompteDto> comptes,
                                                         List<ContactDto> contacts,
                                                         List<ResponsableDto> responsables,
                                                         List<AdresseDto> adresses,
                                                         List<DeviseDto> devises);

    public List<DocumentCommercialResponse> entitiesToResponse(List<DocumentCommercial> documentCommercials) {
        List<Long> compteIdList = documentCommercials.stream().filter(t -> t.getCompteId() != null).map(t -> t.getCompteId()).distinct().collect(Collectors.toList());
        List<Long> deviseIdList = documentCommercials.stream().filter(t -> t.getDeviseId() != null).map(t -> t.getDeviseId()).distinct().collect(Collectors.toList());
        List<Long> responsableIdList = documentCommercials.stream().filter(t -> t.getResponsableId() != null).map(t -> t.getResponsableId()).distinct().collect(Collectors.toList());
        List<Long> contactIdList = documentCommercials.stream().filter(t -> t.getContactId() != null).map(t -> t.getContactId()).distinct().collect(Collectors.toList());
        List<Long> adresseIds = documentCommercials.stream().filter(t -> t.getAdresseId() != null).map(c -> c.getAdresseId()).collect(Collectors.toList());
        adresseIds.addAll(documentCommercials.stream().filter(c -> c.getAdresseLivraisonId() != null).map(c -> c.getAdresseLivraisonId()).collect(Collectors.toList()));

        List<CompteDto> compteDtoList = compteIdList.size() > 0 ? compteClient.getComptesById(compteIdList) : new ArrayList<>();
        List<ResponsableDto> responsableDtoList = responsableIdList.size() > 0 ? responsableClient.getResponsablesById(responsableIdList) : new ArrayList<>();
        List<ContactDto> contactDtoList = contactIdList.size() > 0 ? compteClient.getContactsById(contactIdList) : new ArrayList<>();
        List<AdresseDto> adresseDtoList = adresseIds.size() > 0 ? compteClient.getAdressesById(adresseIds) : new ArrayList<>();
        List<DeviseDto> deviseDtoList = deviseIdList.size() > 0 ? transactionClient.getDevisesById(deviseIdList) : new ArrayList<>();


        return documentCommercials.stream().map(documentCommercial ->
                        entityToResponse(documentCommercial, compteDtoList,
                                contactDtoList, responsableDtoList,
                                adresseDtoList, deviseDtoList))
                .collect(Collectors.toList());
    }

    CompteDto getCompte(List<CompteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    ContactDto getContact(List<ContactDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    AdresseDto getAdresse(List<AdresseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    ResponsableDto getResponsable(List<ResponsableDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    DeviseDto getDevise(List<DeviseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    Double getMontantHT(List<ArticleCommande> articles) {
        return articles.stream().mapToDouble(value -> value.getPrixUnitaire() * value.getQuantite()).sum();
    }

    Double getMontantTTC(DocumentCommercial documentCommercial) {
        double montantHT = documentCommercial.getArticles().stream().mapToDouble(value -> value.getPrixUnitaire() * value.getQuantite()).sum();
        double taxe = documentCommercial.getTaxes().stream().mapToDouble(value -> value.getValeur()).sum();
        return montantHT + taxe;
    }

    String getEtat(DocumentCommercial documentCommercial) {
        if (documentCommercial.getType() == TypeDocument.DEVIS
                && documentCommercial.isActif()
                && documentCommercial.getDateExpiration().isBefore(LocalDate.now())) {
            return EtatDocument.EXPIRE.name();
        }
        return documentCommercial.getEtat().name();
    }
}
