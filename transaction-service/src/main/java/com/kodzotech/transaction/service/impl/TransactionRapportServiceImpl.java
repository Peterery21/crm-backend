package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.client.CompteClient;
import com.kodzotech.transaction.client.EntiteClient;
import com.kodzotech.transaction.mapper.TransactionRapportMapper;
import com.kodzotech.transaction.model.CategorieTransaction;
import com.kodzotech.transaction.model.SensType;
import com.kodzotech.transaction.utils.TresosoftConstant;
import com.kodzotech.transaction.dto.rapport.ResultatExploitationDto;
import com.kodzotech.transaction.dto.rapport.TransactionCategorieDto;
import com.kodzotech.transaction.dto.rapport.TransactionCompteBancaireDto;
import com.kodzotech.transaction.dto.rapport.TransactionJourDto;
import com.kodzotech.transaction.model.Transaction;
import com.kodzotech.transaction.repository.TransactionRepository;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.service.ModePaiementService;
import com.kodzotech.transaction.service.TransactionRapportService;
import com.kodzotech.transaction.service.TransactionSoldeService;
import com.kodzotech.security.jwt.JwtProvider;
import com.kodzotech.transaction.dto.*;
import com.kodzotech.transaction.repository.CategorieTransactionRepository;
import com.kodzotech.transaction.service.CompteBancaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionRapportServiceImpl implements TransactionRapportService {

    private final TransactionRepository transactionRepository;
    private final CategorieTransactionRepository categorieTransactionRepository;
    private final TransactionRapportMapper transactionRapportMapper;
    private final TransactionSoldeService transactionSoldeService;

    private final DeviseService deviseService;
    private final CompteClient compteClient;
    private final CompteBancaireService compteBancaireService;
    private final ModePaiementService modePaiementService;

    private final EntiteClient entiteClient;

    @Override
    public List<TransactionCategorieDto> getTransactionCategorieParPeriode(Long deviseId, String sensType, LocalDate dateDebut, LocalDate dateFin) {
        int sens = (sensType.equalsIgnoreCase(SensType.CATEG_DEPENSE) ? -1 : 1);
        return transactionRepository.totalParCategorie(deviseId, sens, dateDebut, dateFin).stream().map(transactionCategorieDto -> {
            transactionCategorieDto.setTransactions(transactionRepository.getDetailParCategorie(deviseId, sens, transactionCategorieDto.getId(), dateDebut, dateFin));
            return transactionCategorieDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TransactionJourDto> getTransactionCategorieParJour(Long deviseId, String sensType, LocalDate dateDebut, LocalDate dateFin) {
        int sens = (sensType.equalsIgnoreCase(SensType.CATEG_DEPENSE) ? -1 : 1);

        List<TransactionJourDto> transactionJourDtoList = new ArrayList<>();
        //Récupération de la liste des dates utilisée
        List<LocalDate> dateTransactionList = transactionRepository
                .getDateTransactionBetweenDate(deviseId, sens, dateDebut, dateFin);
        //Récupération de la liste des catégories utilisée
        List<Long> categorieTransactionIds = transactionRepository
                .getCategorieTransactionUtilise(deviseId, sens, dateDebut, dateFin);

        //Pour chaque date, on récupère les valeur par catégorie
        //Parcours des dates
        dateTransactionList.stream().forEach(localDate -> {
            //Parcours des catégories
            categorieTransactionIds.stream().forEach(categorieTransactionId -> {
                //Pour chaque catégorie et date récupérer la valeur
                CategorieTransaction categorieTransaction = categorieTransactionRepository
                        .findById(categorieTransactionId).orElse(null);
                Double valeur = transactionRepository.totalCategorieParMois(deviseId, sens,
                        categorieTransactionId, localDate);
                TransactionJourDto transactionJourDto = TransactionJourDto
                        .builder()
                        .dateTransaction(localDate)
                        .valeur(valeur == null ? 0d : valeur)
                        .id(categorieTransactionId)
                        .categorie(categorieTransaction.getLibelle())
                        .build();
                //Récupérer le détail des transactions
                transactionJourDto.setTransactions(transactionRepository
                        .getDetailParCategorie(deviseId, sens, transactionJourDto.getId(),
                                transactionJourDto.getDateTransaction(),
                                transactionJourDto.getDateTransaction()));
                transactionJourDtoList.add(transactionJourDto);
            });
        });

        return transactionJourDtoList;
    }

    @Override
    public List<TransactionCompteBancaireDto> getTransactionParBanque(List<Long> compteBancaireIds,
                                                                      LocalDate dateDebut, LocalDate dateFin) {
        List<Transaction> transactionList = transactionRepository
                .getTransactionParCompteBancaire(compteBancaireIds, dateDebut, dateFin);
        List<Long> deviseIdList = transactionList.stream().map(t -> t.getDeviseId()).distinct().collect(Collectors.toList());
        List<Long> compteIdList = transactionList.stream().map(t -> t.getCompteId()).distinct().collect(Collectors.toList());
        List<Long> compteBancaireIdList = transactionList.stream().map(t -> t.getCompteBancaireId()).distinct().collect(Collectors.toList());
        List<Long> modePaiementIdList = transactionList.stream().map(t -> t.getModePaiementId()).distinct().collect(Collectors.toList());

        List<DeviseDto> deviseDtoList = deviseService.getDevisesById(deviseIdList);
        List<CompteDto> compteDtoList = compteClient.getComptesById(compteIdList);
        List<ModePaiementDto> modePaiementDtoList = modePaiementService.getModePaiementsById(modePaiementIdList);
        List<CompteBancaireDto> compteBancaireDtoList = compteBancaireService.getCompteBancairesById(compteBancaireIdList);

        Map<Long, List<Transaction>> transactionMaps = transactionList.stream().collect(groupingBy(Transaction::getCompteBancaireId));

        return transactionMaps.keySet().stream().flatMap(compteBancaireId -> {
            CompteBancaireDto compteBancaireDto = compteBancaireDtoList.stream()
                    .filter(c -> c.getId().equals(compteBancaireId))
                    .findFirst().orElse(null);
            DeviseDto deviseDto = deviseDtoList.stream()
                    .filter(c -> c.getId().equals(compteBancaireDto.getDeviseId()))
                    .findFirst().orElse(null);
            return Stream.of(TransactionCompteBancaireDto.builder()
                    .valeur(transactionSoldeService.getSoldeCompteBancaireParDate(compteBancaireId, dateDebut))
                    .compteBancaire(compteBancaireDto.getLibelle())
                    .devise(deviseDto.getCode())
                    .transactions(transactionMaps
                            .get(compteBancaireId)
                            .stream().map(transaction -> transactionRapportMapper
                                    .entityToResponse(transaction, deviseDtoList, compteDtoList, modePaiementDtoList, compteBancaireDtoList))
                            .collect(Collectors.toList()))
                    .build());
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResultatExploitationDto getResultatExploitation(Long deviseId, LocalDate dateDebut, LocalDate dateFin) {
        return ResultatExploitationDto.builder()
                .depenses(transactionRepository.totalParCategorie(deviseId, -1, dateDebut, dateFin))
                .recettes(transactionRepository.totalParCategorie(deviseId, 1, dateDebut, dateFin))
                .build();
    }


    @Override
    public byte[] printTransactionCategorieParJour(Long deviseId, String sensType,
                                                   LocalDate dateDebut, LocalDate dateFin,
                                                   HttpServletRequest request) throws JRException, FileNotFoundException {
        String lang = request.getHeader("lang");
        Locale locale = Locale.forLanguageTag(lang);
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        Map par = new HashMap<>();
        par.put(JRParameter.REPORT_LOCALE, locale);
        SocieteDto societeDto = entiteClient.getSociete(societeId);
        par.put("societe", societeDto);
        List<TransactionJourDto> contactResponseList = getTransactionCategorieParJour(deviseId, sensType, dateDebut, dateFin);
        JasperPrint compteReport =
                JasperFillManager.fillReport(JasperCompileManager.compileReport(
                                ResourceUtils.getFile(
                                                "classpath:templates/contact_list.jrxml")
                                        .getAbsolutePath()) // path of the jasper report
                        , par // dynamic parameters
                        , new JRBeanCollectionDataSource(contactResponseList)
                );

        //create the report in PDF format
        return JasperExportManager.exportReportToPdf(compteReport);
    }
}
