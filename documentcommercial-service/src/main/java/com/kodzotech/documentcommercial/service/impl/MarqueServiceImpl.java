package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.MarqueDto;
import com.kodzotech.documentcommercial.exception.MarqueException;
import com.kodzotech.documentcommercial.repository.MarqueRepository;
import com.kodzotech.documentcommercial.mapper.MarqueMapper;
import com.kodzotech.documentcommercial.model.Marque;
import com.kodzotech.documentcommercial.service.ArticleService;
import com.kodzotech.documentcommercial.service.MarqueService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarqueServiceImpl implements MarqueService {

    private final MarqueRepository marqueRepository;
    private final MarqueMapper marqueMapper;
    private final ArticleService articleService;


    @Override
    @Transactional
    public void save(MarqueDto marqueDto) {
        Validate.notNull(marqueDto);
        Marque marque = marqueMapper.dtoToEntity(marqueDto);
        validerMarque(marque);
        if (marque.getId() != null) {
            Marque marqueOriginal = marqueRepository
                    .findById(marque.getId()).get();
            marqueOriginal = marqueMapper.dtoToEntity(marqueOriginal, marque);
            marqueRepository.save(marqueOriginal);
        } else {
            marqueRepository.save(marque);
        }
    }

    @Override
    public void validerMarque(Marque marque) {

        if (marque.getLibelle() == null || marque.getLibelle().isEmpty()) {
            throw new MarqueException("erreur.marque.libelle.null");
        }

        if (marque.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Marque marqueOriginal = marqueRepository
                    .findById(marque.getId())
                    .orElseThrow(() ->
                            new MarqueException("erreur.marque.id.non.trouve"));

            //Vérifier si libellé en double
            Marque marqueTemp = marqueRepository
                    .findByLibelle(marque.getLibelle())
                    .orElse(null);
            if (marqueTemp != null) {
                if (marqueTemp.getId() != marqueOriginal.getId()) {
                    throw new MarqueException("erreur.marque.libelle.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Marque marqueTemp = marqueRepository
                    .findByLibelle(marque.getLibelle())
                    .orElse(null);
            if (marqueTemp != null) {
                throw new MarqueException("erreur.marque.libelle.doublon");
            }
        }
    }


    @Override
    public MarqueDto getMarque(Long id) {
        Marque marque = marqueRepository.findById(id)
                .orElseThrow(() -> new MarqueException(
                        "erreur.marque.id.non.trouve"));
        return marqueMapper.entityToDto(marque);
    }

    @Override
    public List<MarqueDto> getAllMarques() {
        List<Marque> marqueList = marqueRepository.findAll();
        return marqueList.stream()
                .map(marqueMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Marque marque = marqueRepository.findById(id)
                .orElseThrow(
                        () -> new MarqueException("erreur.marque.id.non.trouve"));
        if (articleService.checkUsedMarque(id)) {
            throw new MarqueException("erreur.marque.utilise.compte");
        }
        marqueRepository.delete(marque);
    }
}
