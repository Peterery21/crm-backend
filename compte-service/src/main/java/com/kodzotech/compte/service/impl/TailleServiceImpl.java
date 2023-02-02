package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.dto.TailleDto;
import com.kodzotech.compte.exception.TailleException;
import com.kodzotech.compte.mapper.TailleMapper;
import com.kodzotech.compte.model.Taille;
import com.kodzotech.compte.repository.TailleRepository;
import com.kodzotech.compte.service.CompteService;
import com.kodzotech.compte.service.TailleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TailleServiceImpl implements TailleService {

    private final TailleRepository tailleRepository;
    private final TailleMapper tailleMapper;
    private final CompteService compteService;


    @Override
    @Transactional
    public void save(TailleDto tailleDto) {
        Validate.notNull(tailleDto);
        Taille taille = tailleMapper.dtoToEntity(tailleDto);
        validerTaille(taille);
        if (taille.getId() != null) {
            Taille tailleOriginal = tailleRepository
                    .findById(taille.getId()).get();
            tailleOriginal = tailleMapper.dtoToEntity(tailleOriginal, taille);
            tailleRepository.save(tailleOriginal);
        } else {
            tailleRepository.save(taille);
        }
    }

    @Override
    public void validerTaille(Taille taille) {

        if (taille.getLibelle() == null || taille.getLibelle().isEmpty()) {
            throw new TailleException("erreur.taille.libelle.null");
        }

        if (taille.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Taille tailleOriginal = tailleRepository
                    .findById(taille.getId())
                    .orElseThrow(() ->
                            new TailleException("erreur.taille.id.non.trouve"));

            //Vérifier si libellé en double
            Taille tailleTemp = tailleRepository
                    .findByLibelle(taille.getLibelle())
                    .orElse(null);
            if (tailleTemp != null) {
                if (tailleTemp.getId() != tailleOriginal.getId()) {
                    throw new TailleException("erreur.taille.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Taille tailleTemp = tailleRepository
                    .findByLibelle(taille.getLibelle())
                    .orElse(null);
            if (tailleTemp != null) {
                throw new TailleException("erreur.taille.libelle.doublon");
            }
        }
    }


    @Override
    public TailleDto getTaille(Long id) {
        Taille taille = tailleRepository.findById(id)
                .orElseThrow(() -> new TailleException(
                        "erreur.taille.id.non.trouve"));
        return tailleMapper.entityToDto(taille);
    }

    @Override
    public List<TailleDto> getAllTailles() {
        List<Taille> tailleList = tailleRepository.findAll();
        return tailleList.stream()
                .map(tailleMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Taille taille = tailleRepository.findById(id)
                .orElseThrow(
                        () -> new TailleException("erreur.taille.id.non.trouve"));
        if (compteService.checkUsedTaille(id)) {
            throw new TailleException("erreur.taille.utilise.compte");
        }
        tailleRepository.delete(taille);
    }
}
