package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.client.ResponsableClient;
import com.kodzotech.compte.client.TransactionClient;
import com.kodzotech.compte.dto.*;
import com.kodzotech.compte.exception.CategorieCompteException;
import com.kodzotech.compte.exception.CompteException;
import com.kodzotech.compte.mapper.CompteMapper;
import com.kodzotech.compte.model.Compte;
import com.kodzotech.compte.model.CompteType;
import com.kodzotech.compte.repository.CompteRepository;
import com.kodzotech.compte.service.AdresseService;
import com.kodzotech.compte.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;
    private final AdresseService adresseService;
    private final ResponsableClient responsableClient;
    private final TransactionClient transactionClient;

    @Override
    @Transactional
    public void save(CompteDto compteDto) {
        Compte compte = compteMapper.dtoToEntity(compteDto);
        validerCompte(compte);

        AdresseDto adresseDto = compteDto.getAdresse();
        AdresseDto adresseLivraisonDto = compteDto.getAdresseLivraison();
        //Update adresse
        Long adresseId = null;
        Long adresseLivraisonId = null;
        if (adresseDto != null) {
            if (adresseDto.getId() != null) {
                adresseId = adresseService.save(adresseDto);
            } else {
                adresseId = adresseService.save(adresseDto);
            }
            compte.setAdresseId(adresseId);
        }
        if (adresseLivraisonDto != null) {
            if (adresseLivraisonDto.getId() != null) {
                adresseLivraisonId = adresseService.save(adresseLivraisonDto);
            } else {
                adresseLivraisonId = adresseService.save(adresseLivraisonDto);
            }
            compte.setAdresseLivraisonId(adresseLivraisonId);
        }
        if (compte.getId() != null) {
            Compte compteOriginal = compteRepository
                    .findById(compte.getId()).get();
            compteOriginal = compteMapper.dtoToEntity(compteOriginal, compte);
            //Save compte
            compteRepository.save(compteOriginal);
        } else {
            //Save compte
            compteRepository.save(compte);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerCompte(Compte compte) {
        if (compte.getCategorieCompte() == null) {
            throw new CompteException("erreur.compte.categorie.non.valide");
        }
        if (compte.getRaisonSociale() == null || compte.getRaisonSociale().isEmpty()) {
            throw new CompteException("erreur.compte.raisonsociale.non.valide");
        }
        if (compte.getType() == null) {
            throw new CompteException("erreur.compte.type.non.valide");
        }
        if (compte.getResponsableId() != null) {
            //Vérification de l'existance de l'utilisateur
            ResponsableDto responsableDto = responsableClient
                    .getResponsable(compte.getResponsableId());
            if (responsableDto == null) {
                throw new CompteException("erreur.compte.responsable.non.valide");
            }
        }
        if (compte.getAdresseIdentique() == null) {
            throw new CompteException("erreur.compte.adresseIdentique.non.valide");
        }
        if (compte.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Compte compteOriginal = compteRepository
                    .findById(compte.getId())
                    .orElseThrow(() ->
                            new CompteException("erreur.compte.id.non.trouve"));

            //Vérifier si libellé en double
            Compte compteTemp = compteRepository
                    .findByRaisonSociale(compte.getRaisonSociale())
                    .orElse(null);
            if (compteTemp != null) {
                if (compteTemp.getId() != compteOriginal.getId()) {
                    throw new CompteException("erreur.compte.raisonsociale.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Compte compteTemp = compteRepository
                    .findByRaisonSociale(compte.getRaisonSociale())
                    .orElse(null);
            if (compteTemp != null) {
                throw new CompteException("erreur.compte.raisonsociale.doublon");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompteDto getCompte(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new CompteException(
                        "erreur.compte.id.non.trouve"));
        return compteMapper.entityToDto(compte);
    }

    @Override
    @Transactional(readOnly = true)
    public CompteResponse getFullCompte(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new CompteException(
                        "erreur.compte.id.non.trouve"));
        return compteMapper.entitiesToResponse(Arrays.asList(compte)).get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> getAllCompte(int page, int size) {
        Pageable sortedByDateDesc =
                PageRequest.of(page, size, Sort.by("raisonSociale").ascending());
        List<Compte> compteList = compteRepository.findAll(sortedByDateDesc).toList();
        return compteMapper.entitiesToResponse(compteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> getAllCompte() {
        List<Compte> compteList = compteRepository.findAll();
        return compteMapper.entitiesToResponse(compteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> getAllClient() {
        List<Compte> compteList = compteRepository.findAllByType(CompteType.CLIENT);
        return compteMapper.entitiesToResponse(compteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> getAllProspect() {
        List<Compte> compteList = compteRepository.findAllByType(CompteType.PROSPECT);
        return compteMapper.entitiesToResponse(compteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> getAllFournisseur() {
        List<Compte> compteList = compteRepository.findAllByType(CompteType.FOURNISSEUR);
        return compteMapper.entitiesToResponse(compteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteDto> getComptesById(List<Long> ids) {
        return compteMapper.entitiesToDto(compteRepository.findAllByIdIn(ids));
    }

    @Override
    @Transactional(readOnly = true)
    public StatCompteDto getStatCompte() {
        return StatCompteDto.builder()
                .clients(compteRepository.countByType(CompteType.CLIENT))
                .fournisseurs(compteRepository.countByType(CompteType.FOURNISSEUR))
                .prospects(compteRepository.countByType(CompteType.PROSPECT))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNbreComptes() {
        return compteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedSecteur(Long id) {
        return compteRepository.existsBySecteurActiviteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedCategorie(Long id) {
        return compteRepository.existsByCategorieCompteId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedTaille(Long id) {
        return compteRepository.existsByTailleId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedAdresse(Long id) {
        return compteRepository.existsByAdresseIdOrAdresseLivraisonId(id, id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(
                        () -> new CompteException("erreur.compte.id.non.trouve"));
        if (transactionClient.checkUsedCompte(id)) {
            throw new CategorieCompteException("erreur.compte.utilise.transaction");
        }
        compteRepository.delete(compte);
    }
}
