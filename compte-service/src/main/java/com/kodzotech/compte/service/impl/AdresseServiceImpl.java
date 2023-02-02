package com.kodzotech.compte.service.impl;

import com.kodzotech.compte.dto.AdresseDto;
import com.kodzotech.compte.exception.AdresseException;
import com.kodzotech.compte.mapper.AdresseMapper;
import com.kodzotech.compte.model.Adresse;
import com.kodzotech.compte.repository.AdresseRepository;
import com.kodzotech.compte.repository.CompteRepository;
import com.kodzotech.compte.service.AdresseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository adresseRepository;
    private final AdresseMapper adresseMapper;
    private final CompteRepository compteRepository;

    @Override
    @Transactional
    public Long save(AdresseDto adresseDto) {
        Validate.notNull(adresseDto);
        Adresse adresse = adresseMapper.dtoToEntity(adresseDto);
        validerAdresse(adresse);
        Long id = null;
        if (adresse.getId() != null) {
            Adresse adresseOriginal = adresseRepository
                    .findById(adresse.getId()).get();
            adresseOriginal = adresseMapper.fillEntity(adresseOriginal, adresse);
            adresseRepository.save(adresseOriginal);
            id = adresseOriginal.getId();
        } else {
            adresseRepository.save(adresse);
            id = adresse.getId();
        }
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public void validerAdresse(Adresse adresse) {

        if (adresse.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Adresse adresseOriginal = adresseRepository
                    .findById(adresse.getId())
                    .orElseThrow(() ->
                            new AdresseException("erreur.adresse.id.non.trouve"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AdresseDto getAdresse(Long id) {
        Adresse adresse = adresseRepository.findById(id)
                .orElseThrow(() -> new AdresseException(
                        "erreur.adresse.id.non.trouve"));
        return adresseMapper.entityToDto(adresse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseDto> getAllAdresses() {
        List<Adresse> adresseList = adresseRepository.findAll();
        return adresseList.stream()
                .map(adresseMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Adresse adresse = adresseRepository.findById(id)
                .orElseThrow(
                        () -> new AdresseException("erreur.adresse.id.non.trouve"));
        if (compteRepository.existsByAdresseIdOrAdresseLivraisonId(id, id)) {
            throw new AdresseException("erreur.adresse.utilise.compte");
        }
        adresseRepository.delete(adresse);
    }

    @Override
    public List<AdresseDto> getAdressesById(List<Long> ids) {
        return adresseRepository.findAllByIdIn(ids)
                .stream()
                .map(adresseMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
