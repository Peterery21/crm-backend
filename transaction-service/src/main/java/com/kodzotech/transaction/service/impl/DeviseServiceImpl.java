package com.kodzotech.transaction.service.impl;

import com.kodzotech.transaction.exception.DeviseException;
import com.kodzotech.transaction.mapper.DeviseMapper;
import com.kodzotech.transaction.model.Devise;
import com.kodzotech.transaction.repository.DeviseRepository;
import com.kodzotech.transaction.service.DeviseService;
import com.kodzotech.transaction.dto.DeviseDto;
import com.kodzotech.transaction.repository.TransactionRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviseServiceImpl implements DeviseService {

    private final DeviseRepository deviseRepository;
    private final DeviseMapper deviseMapper;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void save(DeviseDto deviseDto) {
        Devise devise = deviseMapper.dtoToEntity(deviseDto);
        validerDevise(devise);
        if (devise.getId() != null) {
            Devise deviseOriginal = deviseRepository
                    .findById(devise.getId()).get();
            deviseOriginal.setCode(devise.getCode());
            deviseOriginal.setLibelle(devise.getLibelle());
            deviseOriginal.setSymbole(devise.getSymbole());
            deviseOriginal.setPays(devise.getPays());
            deviseOriginal.setUnite(devise.getUnite());
            deviseRepository.save(deviseOriginal);
        } else {
            deviseRepository.save(devise);
        }
        deviseRepository.save(devise);
    }

    @Override
    @Transactional(readOnly = true)
    public void validerDevise(Devise devise) {

        if (devise.getId() != null) {
            // Mode modification
            //Rechercher la devise de la base
            Devise deviseOriginal = deviseRepository
                    .findById(devise.getId())
                    .orElseThrow(() ->
                            new DeviseException("erreur.devise.id.non.trouve"));
            //Vérifier si code et pays en double
            Devise deviseTemp = deviseRepository
                    .findByCodeAndPays(devise.getCode(), devise.getPays())
                    .orElse(null);
            if (deviseTemp != null) {
                if (deviseTemp.getId() != deviseOriginal.getId()) {
                    throw new DeviseException("erreur.devise.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Devise deviseTemp = deviseRepository
                    .findByCodeAndPays(devise.getCode(), devise.getPays())
                    .orElse(null);
            if (deviseTemp != null) {
                throw new DeviseException("erreur.devise.doublon");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeviseDto getDevise(Long id) {
        return deviseMapper.entityToDto(deviseRepository.findById(id)
                .orElseThrow(() ->
                        new DeviseException("erreur.devise.id.non.trouve")));
    }

    @Override
    public List<DeviseDto> getDefaultList() {
        String currencyJson = resourceToFile("currency.json");
        Type listDeviseType = new TypeToken<ArrayList<DeviseDto>>() {
        }.getType();
        List<DeviseDto> deviseDtoList = new Gson().fromJson(currencyJson, listDeviseType);
//        deviseDtoList = deviseDtoList.stream().distinct()
//                .collect(Collectors.groupingBy(p -> p.getCode()))
//                .values().stream().map(p -> p.stream().findFirst().get())
//                .collect(Collectors.toList());
        return deviseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviseDto> getAllDevise() {
        return deviseRepository.findAll()
                .stream().map(deviseMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Devise devise = deviseRepository.findById(id)
                .orElseThrow(
                        () -> new DeviseException("erreur.devise.id.non.trouve"));
        if (transactionRepository.existsByDeviseId(id)) {
            throw new DeviseException("erreur.devise.utilise.transaction");
        }
        deviseRepository.delete(devise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviseDto> getDevisesById(List<Long> ids) {
        return deviseRepository.findAllByIdIn(ids)
                .stream()
                .map(deviseMapper::entityToDto)
                .collect(Collectors.toList());
    }

    String resourceToFile(String resource) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
            return IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
