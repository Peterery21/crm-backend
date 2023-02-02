package com.kodzotech.fileupload.service.impl;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.exception.FichierException;
import com.kodzotech.fileupload.mapper.FichierMapper;
import com.kodzotech.fileupload.model.Fichier;
import com.kodzotech.fileupload.service.FichierService;
import com.kodzotech.fileupload.repository.FichierRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FichierServiceImpl implements FichierService {

    private final FichierRepository fichierRepository;
    private final FichierMapper fichierMapper;

    @Override
    @Transactional
    public Long save(FichierDto FichierDto) {
        Validate.notNull(FichierDto);
        Fichier Fichier = fichierMapper.dtoToEntity(FichierDto);
        validerFichier(Fichier);
        Fichier file = null;
        if (Fichier.getId() != null) {
            Fichier fichierOriginal = fichierRepository
                    .findById(Fichier.getId()).get();
            fichierOriginal = fichierMapper.dtoToEntity(fichierOriginal, Fichier);
            file = fichierRepository.save(fichierOriginal);
        } else {
            file = fichierRepository.save(Fichier);
        }
        return file.getId();
    }

    @Override
    public void validerFichier(Fichier fichier) {
        if (fichier.getFileUrl() == null) {
            throw new FichierException("erreur.fichier.fileUrl.incorrect");
        }
        if (fichier.getCategorie() == null) {
            throw new FichierException("erreur.fichier.categorie.incorrect");
        }
        if (fichier.getIdObjet() == null || fichier.getIdObjet() == 0) {
            throw new FichierException("erreur.fichier.idObjet.incorrect");
        }
    }

    @Override
    @Transactional
    public void deleteByCategorieAndIdObjet(String categorie, Long idObjet) {
        fichierRepository.deleteByCategorieAndIdObjet(categorie, idObjet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichierDto> getDocuments(String categorie, Long idObjet) {
        List<Fichier> fichierList = fichierRepository.findByCategorieAndIdObjet(categorie, idObjet);
        return fichierList.stream().map(fichierMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichierDto> getDocumentByidObjet(String categorie, List<Long> idObjets) {
        List<Fichier> fichierList = fichierRepository.findByCategorieAndIdObjetIn(categorie, idObjets);
        return fichierList.stream().map(fichierMapper::entityToDto).collect(Collectors.toList());
    }


}
