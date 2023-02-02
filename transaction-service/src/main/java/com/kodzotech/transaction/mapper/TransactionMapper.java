package com.kodzotech.transaction.mapper;

import com.kodzotech.transaction.service.CompteBancaireService;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.service.ModePaiementService;
import com.kodzotech.transaction.client.CompteClient;
import com.kodzotech.transaction.client.DocumentCommercialClient;
import com.kodzotech.transaction.dto.*;
import com.kodzotech.transaction.model.CategorieTransaction;
import com.kodzotech.transaction.model.Transaction;
import com.kodzotech.transaction.repository.CategorieTransactionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Autowired
    private CategorieTransactionRepository categorieTransactionRepository;
    @Autowired
    private CategorieTransactionMapper categorieTransactionMapper;
    @Autowired
    private DeviseService deviseService;
    @Autowired
    private CompteClient compteClient;
    @Autowired
    private CompteBancaireService compteBancaireService;
    @Autowired
    private ModePaiementService modePaiementService;
    @Autowired
    private DocumentCommercialClient documentCommercialClient;

    @Mapping(target = "categorieTransaction", expression = "java(getCategorie(transactionDto.getCategorieId()))")
    @Mapping(target = "sens", ignore = true)
    @Mapping(target = "numTransaction", ignore = true)
    public abstract Transaction dtoToEntity(TransactionDto transactionDto);

    @Mapping(target = "categorieId", source = "categorieTransaction.id")
    public abstract TransactionDto entityToDto(Transaction transaction);

    public CategorieTransaction getCategorie(Long categorieId) {
        if (categorieId != null) {
            return categorieTransactionRepository
                    .findById(categorieId).orElse(null);
        }
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Transaction dtoToEntity(@MappingTarget Transaction transactionOriginal,
                                            Transaction transactionModifier);

    @Mapping(target = "compte", expression = "java(getCompte(compteDtoList, transaction.getCompteId()))")
    @Mapping(target = "devise", expression = "java(getDevise(deviseDtoList, transaction.getDeviseId()))")
    @Mapping(target = "modePaiement", expression = "java(getModePaiement(modePaiementDtoList, transaction.getModePaiementId()))")
    @Mapping(target = "compteBancaire", expression = "java(getCompteBancaire(compteBancaireDtoList, transaction.getCompteBancaireId()))")
    @Mapping(target = "documentCommercial", expression = "java(getDocument(documentCommercialDtoList, transaction.getDocumentId()))")
    public abstract TransactionResponse entityToResponse(Transaction transaction,
                                                         List<DeviseDto> deviseDtoList,
                                                         List<CompteDto> compteDtoList,
                                                         List<ModePaiementDto> modePaiementDtoList,
                                                         List<CompteBancaireDto> compteBancaireDtoList,
                                                         List<DocumentCommercialDto> documentCommercialDtoList);


    public List<TransactionResponse> entityToResponse(List<Transaction> transactionList) {
        List<Long> deviseIdList = transactionList.stream().map(t -> t.getDeviseId()).distinct().collect(Collectors.toList());
        List<Long> compteIdList = transactionList.stream().map(t -> t.getCompteId()).distinct().collect(Collectors.toList());
        List<Long> compteBancaireIdList = transactionList.stream().map(t -> t.getCompteBancaireId()).distinct().collect(Collectors.toList());
        List<Long> modePaiementIdList = transactionList.stream().map(t -> t.getModePaiementId()).distinct().collect(Collectors.toList());
        List<Long> documentIdList = transactionList.stream().map(t -> t.getDocumentId()).distinct().collect(Collectors.toList());

        List<DeviseDto> deviseDtoList = deviseService.getDevisesById(deviseIdList);
        List<CompteDto> compteDtoList = compteClient.getComptesById(compteIdList);
        List<ModePaiementDto> modePaiementDtoList = modePaiementService.getModePaiementsById(modePaiementIdList);
        List<CompteBancaireDto> compteBancaireDtoList = compteBancaireService.getCompteBancairesById(compteBancaireIdList);
        List<DocumentCommercialDto> documentCommercialDtoList = documentCommercialClient.getDocumentsById(documentIdList);

        List<TransactionResponse> transactionResponseList = transactionList.stream()
                .flatMap(transaction -> Stream.of(entityToResponse(transaction, deviseDtoList, compteDtoList,
                        modePaiementDtoList, compteBancaireDtoList, documentCommercialDtoList)))
                .collect(Collectors.toList());
        return transactionResponseList;
    }

    DeviseDto getDevise(List<DeviseDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    CompteDto getCompte(List<CompteDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    ModePaiementDto getModePaiement(List<ModePaiementDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    CompteBancaireDto getCompteBancaire(List<CompteBancaireDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }

    DocumentCommercialDto getDocument(List<DocumentCommercialDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }
}
